package com.example.projectbogdan.data

import com.example.projectbogdan.data.model.FavouriteProduct
import com.example.projectbogdan.data.model.ShoppingProduct
import com.example.projectbogdan.data.model.StorageProduct
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ProductsDataSource {
    val firestore = Firebase.firestore

    suspend fun getFavouriteList(userId: String) : QuerySnapshot? {
        return try {
            val data = firestore
                .collection("users")
                .document(userId)
                .collection("favouriteList")
                .get()
                .await()
            data

        } catch(e: Exception){
            null
        }
    }

    suspend fun getShoppingList(userId: String) : QuerySnapshot? {
        return try {
            val data = firestore
                .collection("users")
                .document(userId)
                .collection("shoppingList")
                .get()
                .await()
            data

        } catch(e: Exception){
            null
        }
    }

    suspend fun getStorageList(userId: String) : QuerySnapshot? {
        return try {
            val data = firestore
                .collection("users")
                .document(userId)
                .collection("storageList")
                .get()
                .await()
            data
        } catch(e: Exception){
            null
        }
    }

    suspend fun removeFromFavouriteList(userId: String, product: FavouriteProduct) : Boolean {
        return try {
            val data2 = firestore
                .collection("users")
                .document(userId)
                .collection("favouriteList")
                .document(product.documentId)
                .delete()
                .await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun removeFromShoppingList(userId: String, product: ShoppingProduct) : Boolean {
        return try {
            val data = firestore
                .collection("users")
                .document(userId)
                .collection("shoppingList")
                .document(product.documentId)
                .delete()
                .await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun removeFromStorageList(userId: String, product: StorageProduct) : Boolean {
        return try {
            val data = firestore
                .collection("users")
                .document(userId)
                .collection("storageList")
                .document(product.documentId)
                .delete()
                .await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun addMultipleProductsToShoppingList(userId: String, products: List<ShoppingProduct>): Boolean{
        return try {
            val batch = firestore.batch()
            products.forEach {
                shoppingProduct ->
                    val docRef = firestore.collection("users")
                        .document(userId)
                        .collection("shoppingList")
                        .document()
                    batch.set(docRef, shoppingProduct)
            }
            batch.commit().await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun addMultipleProductsToStorageList(userId: String, products: List<ShoppingProduct>): Boolean{
        return try {
            val batch = firestore.batch()
            products.forEach {
                    shoppingProduct ->
                val docRef = firestore.collection("users")
                    .document(userId)
                    .collection("storageList")
                    .document()
                batch.set(docRef, shoppingProduct)
            }
            batch.commit().await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun deleteMultipleProductsFromFavouriteList(userId: String, products: List<ShoppingProduct>): Boolean{
        return try {
            val batch = firestore.batch()
            products.forEach {
                    shoppingProduct ->
                val docRef = firestore.collection("users")
                    .document(userId)
                    .collection("favouriteList")
                    .document(shoppingProduct.documentId)
                batch.delete(docRef)
            }
            batch.commit().await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun deleteMultipleProductsFromShoppingList(userId: String, products: List<ShoppingProduct>): Boolean{
        return try {
            val batch = firestore.batch()
            products.forEach {
                    shoppingProduct ->
                val docRef = firestore.collection("users")
                    .document(userId)
                    .collection("shoppingList")
                    .document(shoppingProduct.documentId)
                batch.delete(docRef)
            }
            batch.commit().await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun updateStorageProductDate(userId: String, product: StorageProduct): Boolean{
        return try {
            val data = firestore
                    .collection("users")
                    .document(userId)
                    .collection("storageList")
                    .document(product.documentId)
                .update(mapOf(Pair("expirationDate", product.expirationDate)))
                .await()
            true
        } catch(e: Exception){
            false
        }
    }

    suspend fun updateStorageProductMentionsAndQuantity(userId: String, product: ShoppingProduct): Boolean{
        return try {
            val data = firestore
                .collection("users")
                .document(userId)
                .collection("shoppingList")
                .document(product.documentId)
                .update(mapOf(Pair("mentions", product.mentions), Pair("quantity", product.quantity)))
                .await()
            true
        } catch(e: Exception){
            false
        }
    }
}