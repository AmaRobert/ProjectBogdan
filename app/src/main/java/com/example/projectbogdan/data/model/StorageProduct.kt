package com.example.projectbogdan.data.model

import com.google.firebase.firestore.DocumentId

data class StorageProduct(
    @DocumentId
    val documentId: String = "",
    val name: String = "",
    val category: String = "",
    var mentions: String = "",
    var quantity: String = "",
    var rating: Double = 0.toDouble(),
    var hasExpirationDate: Boolean = false,
    var expirationDate: String = ""
)