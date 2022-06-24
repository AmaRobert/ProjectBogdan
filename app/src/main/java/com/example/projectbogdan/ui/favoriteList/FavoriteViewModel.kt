package com.example.projectbogdan.ui.favoriteList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectbogdan.data.ShoppingRepository
import com.example.projectbogdan.data.model.FavouriteProduct
import com.example.projectbogdan.data.model.ShoppingProduct
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class FavoriteViewModel(private val shoppingRepository: ShoppingRepository) : ViewModel(), FavouriteItemViewHolder.ClickListener {

    private val _favouriteList = MutableLiveData<List<FavouriteProduct>>()
    private val _itemToBeAddedToShoppingList = MutableLiveData<List<ShoppingProduct>>()
    private val _isShowingProgress = MutableLiveData<Boolean>()

    val favouriteList : LiveData<List<FavouriteProduct>> = _favouriteList
    val itemToBeAddedToShoppingList : LiveData<List<ShoppingProduct>> = _itemToBeAddedToShoppingList
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
            val favouriteList = shoppingRepository.getFavouriteList(userId)
            _favouriteList.postValue(favouriteList)
            _isShowingProgress.postValue(false)
        }
    }

    fun addAllToShoppingList(){
        val userId = mAuth.currentUser?.uid
        if(userId == null){
            return
        }
        val itemsToBeAdded = _itemToBeAddedToShoppingList.value
        if(itemsToBeAdded.isNullOrEmpty()){
            return
        }
        viewModelScope.launch {
            _isShowingProgress.postValue(true)
            val isAdded = shoppingRepository.addMultipleProductsToShoppingList(userId, itemsToBeAdded)
            if(isAdded){
                _itemToBeAddedToShoppingList.postValue(emptyList())
            }
            _isShowingProgress.postValue(false)
        }

    }

    override fun onItemClicked(item: FavouriteProduct) {
        Log.d("NUSH", "onItemClicked")
    }

    override fun onDeleteItemClicked(item: FavouriteProduct) {
        Log.d("NUSH", "onDeleteItemClicked")
        val userId = mAuth.currentUser?.uid
        if(userId == null){
            return
        }
        viewModelScope.launch {
            _isShowingProgress.postValue(true)
            val isDeleted = shoppingRepository.removeProductFromFavouriteList(userId, item)
            if (isDeleted){
                val temporaryFavouriteList = _favouriteList.value as MutableList
                if(temporaryFavouriteList.isNotEmpty()){
                    temporaryFavouriteList.removeIf { it.documentId == item.documentId }
                    _favouriteList.postValue(temporaryFavouriteList)
                }
            }
            _isShowingProgress.postValue(false)
        }
    }

    override fun onAddItemToShoppingListClicked(item: FavouriteProduct, isAdded: Boolean) {
        Log.d("NUSH", "onAddItemToShoppingListClicked")
        val itemToBeAddedToShoppingList : MutableList<ShoppingProduct> =
            (_itemToBeAddedToShoppingList.value ?: mutableListOf()) as MutableList<ShoppingProduct>
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
        _itemToBeAddedToShoppingList.postValue(itemToBeAddedToShoppingList)
    }
}