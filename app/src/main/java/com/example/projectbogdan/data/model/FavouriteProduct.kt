package com.example.projectbogdan.data.model
import com.example.projectbogdan.data.Constants
import com.google.firebase.firestore.DocumentId

data class FavouriteProduct(
    @DocumentId
    val documentId: String = "",
    val name: String = "",
    val category: String = "",
    var mentions: String = "",
    var quantity: String = "",
    var rating: Double = 0.toDouble(),
    var hasDate: Boolean = false
)
