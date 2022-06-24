package com.example.projectbogdan.ui.productInfoPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectbogdan.data.ProductsDataSource
import com.example.projectbogdan.data.ShoppingRepository

class ProductInfoViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductInfoViewModel::class.java)) {
            return ProductInfoViewModel(
                shoppingRepository = ShoppingRepository(
                    dataSource = ProductsDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}