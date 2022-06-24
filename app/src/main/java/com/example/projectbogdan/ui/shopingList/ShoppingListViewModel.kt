package com.example.projectbogdan.ui.shopingList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectbogdan.data.ShoppingRepository
import com.example.projectbogdan.data.model.ShoppingProduct
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ShoppingListViewModel(private val shoppingRepository: ShoppingRepository) : ViewModel(), ShoppingItemViewHolder.ClickListener {

    private val _shoppingList = MutableLiveData<List<ShoppingProduct>>()
    private val _itemToBeAdded = MutableLiveData<List<ShoppingProduct>>()
    private val _isShowingProgress = MutableLiveData<Boolean>()
    private val _infoClicked = MutableLiveData<ShoppingProduct>()

    val shoppingList :LiveData<List<ShoppingProduct>> = _shoppingList
    val itemToBeAdded : LiveData<List<ShoppingProduct>> = _itemToBeAdded
    val infoClicked : LiveData<ShoppingProduct> = _infoClicked

    val isShowingProgress : LiveData<Boolean> = _isShowingProgress
    val mAuth = FirebaseAuth.getInstance()

    init {
        getShoppingListData()
    }

    fun getShoppingListData(){
        val userId = mAuth.currentUser?.uid
        if(userId == null){
            return
        }
        viewModelScope.launch {
            _isShowingProgress.postValue(true)
            val shoppingList = shoppingRepository.getShoppingList(userId)
            _shoppingList.postValue(shoppingList)
            _isShowingProgress.postValue(false)
        }
    }

    fun finishShopping(){
        val userId = mAuth.currentUser?.uid
        if(userId == null){
            return
        }
        val itemsToBeAdded = _itemToBeAdded.value
        if(itemsToBeAdded.isNullOrEmpty()){
            return
        }
        viewModelScope.launch {
            _isShowingProgress.postValue(true)
            val isAdded = shoppingRepository.addMultipleProductsToStorageList(userId, itemsToBeAdded)
            if(isAdded){
                _itemToBeAdded.postValue(emptyList())
            }
            val isDeleted = shoppingRepository.deleteMultipleProductsFromShoppingList(userId, itemsToBeAdded)
            if(isDeleted){
                val initialList = _shoppingList.value as MutableList
                itemsToBeAdded.forEach {
                    initialList.removeIf { item -> item.documentId == it.documentId}
                }
                _shoppingList.postValue(initialList)
            }
            _isShowingProgress.postValue(false)
        }

    }

    override fun onInfoClicked(item: ShoppingProduct) {
        Log.d("NUSH", "onInfoClicked")
        _infoClicked.postValue(item)
    }

    override fun onDeleteItemClicked(item: ShoppingProduct) {
        Log.d("NUSH", "onDeleteItemClicked")
        val userId = mAuth.currentUser?.uid
        if(userId == null){
            return
        }
        viewModelScope.launch {
            _isShowingProgress.postValue(true)
            val isDeleted = shoppingRepository.removeProductFromShoppingList(userId, item)
            if (isDeleted){
                val temporaryFavouriteList = _shoppingList.value as MutableList
                if(temporaryFavouriteList.isNotEmpty()){
                    temporaryFavouriteList.removeIf { it.documentId == item.documentId }
                    _shoppingList.postValue(temporaryFavouriteList)
                }
            }
            _isShowingProgress.postValue(false)
        }
    }

    override fun onAddItemToStorageListClicked(item: ShoppingProduct, isChecked: Boolean) {
        val itemToBeAddedToShoppingList : MutableList<ShoppingProduct> =
            (_itemToBeAdded.value ?: mutableListOf()) as MutableList<ShoppingProduct>
        if(itemToBeAddedToShoppingList.find { it.documentId == item.documentId } != null){
            itemToBeAddedToShoppingList.removeIf { it.documentId == item.documentId }
        }else{
            itemToBeAddedToShoppingList.add(
                ShoppingProduct(
                    documentId = item.documentId,
                    name = item.name,
                    category = item.category,
                    mentions = item.mentions,
                    quantity = item.quantity,
                    rating = item.rating
                )
            )
        }
        _itemToBeAdded.postValue(itemToBeAddedToShoppingList)
    }
}