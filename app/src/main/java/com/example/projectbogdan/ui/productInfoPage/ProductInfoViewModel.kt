package com.example.projectbogdan.ui.productInfoPage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectbogdan.data.ShoppingRepository
import com.example.projectbogdan.data.model.ShoppingProduct
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProductInfoViewModel(private val shoppingRepository: ShoppingRepository) : ViewModel() {
    private val _isShowingProgress = MutableLiveData<Boolean>()
    private val _product = MutableLiveData<ShoppingProduct>()
    private val _endWithSuccess = MutableLiveData<Boolean>()

    private var _mentions : String? = null
    private var _quantity : String? = null

    val isShowingProgress: LiveData<Boolean> = _isShowingProgress
    val product: LiveData<ShoppingProduct> = _product
    val endWithSuccess : LiveData<Boolean> = _endWithSuccess

    val mAuth = FirebaseAuth.getInstance()

    fun loadPage(product: ShoppingProduct){
        _product.postValue(product)
    }

    fun updateMentionsValue(mentions: String){
        val product = _product.value
        if(product == null){
            return
        }
        _mentions = mentions
    }

    fun updateQuantityValue(quantity: String){
        val product = _product.value
        if(product == null){
            return
        }
        _quantity = quantity
    }

    fun saveProduct(){
        val userId = mAuth.currentUser?.uid
        if(userId == null){
            return
        }
        val product = _product.value
        if(product == null){
            return
        }
        if(_mentions != null){
            product.mentions = _mentions as String
        }
        if(_quantity != null){
            product.quantity = _quantity as String
        }
        viewModelScope.launch {
            _isShowingProgress.postValue(true)
            val isUpdated = shoppingRepository.updateStorageProductMentionsAndQuantity(userId, product)
            if(isUpdated){
                _endWithSuccess.postValue(true)
            }
            _isShowingProgress.postValue(false)
        }
    }
}