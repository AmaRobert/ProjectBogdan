package com.example.projectbogdan.ui.favoriteList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectbogdan.data.ProductsDataSource
import com.example.projectbogdan.data.ShoppingRepository

class FavouriteListViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(
                shoppingRepository = ShoppingRepository(
                    dataSource = ProductsDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}