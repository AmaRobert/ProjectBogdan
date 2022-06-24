package com.example.projectbogdan.data

import com.example.projectbogdan.data.model.FavouriteProduct
import com.example.projectbogdan.data.model.ShoppingProduct
import com.example.projectbogdan.data.model.StorageProduct
import com.google.firebase.firestore.ktx.toObjects

class ShoppingRepository(private val dataSource: ProductsDataSource) {

    suspend fun getShoppingList(userId: String) : List<ShoppingProduct>{
        val querySnapshot = dataSource.getShoppingList(userId)
        return querySnapshot?.toObjects() ?: emptyList()
    }

    suspend fun getFavouriteList(userId: String) : List<FavouriteProduct>{
        val querySnapshot = dataSource.getFavouriteList(userId)
        return querySnapshot?.toObjects() ?: emptyList()
    }

    suspend fun getStorageList(userId: String) : List<StorageProduct>{
        val querySnapshot = dataSource.getStorageList(userId)
        return querySnapshot?.toObjects() ?: emptyList()
    }

    suspend fun removeProductFromFavouriteList(userId: String, product: FavouriteProduct): Boolean {
        return dataSource.removeFromFavouriteList(userId, product)
    }

    suspend fun removeProductFromShoppingList(userId: String, product: ShoppingProduct): Boolean {
        return dataSource.removeFromShoppingList(userId, product)
    }

    suspend fun removeProductFromStorageList(userId: String, product: StorageProduct): Boolean {
        return dataSource.removeFromStorageList(userId, product)
    }

    suspend fun addMultipleProductsToShoppingList(
        userId: String,
        products: List<ShoppingProduct>
    ): Boolean {
        return dataSource.addMultipleProductsToShoppingList(userId, products)
    }

    suspend fun addMultipleProductsToStorageList(
        userId: String,
        products: List<ShoppingProduct>
    ): Boolean {
        return dataSource.addMultipleProductsToStorageList(userId, products)
    }

    suspend fun deleteMultipleProductsFromFavouriteList(
        userId: String,
        products: List<ShoppingProduct>
    ): Boolean {
        return dataSource.deleteMultipleProductsFromFavouriteList(userId, products)
    }

    suspend fun deleteMultipleProductsFromShoppingList(
        userId: String,
        products: List<ShoppingProduct>
    ): Boolean {
        return dataSource.deleteMultipleProductsFromShoppingList(userId, products)
    }

    suspend fun updateStorageProductDate(
        userId: String,
        product: StorageProduct
    ): Boolean {
        return dataSource.updateStorageProductDate(userId, product)
    }

    suspend fun updateStorageProductMentionsAndQuantity(
        userId: String,
        product: ShoppingProduct
    ): Boolean {
        return dataSource.updateStorageProductMentionsAndQuantity(userId, product)
    }
}