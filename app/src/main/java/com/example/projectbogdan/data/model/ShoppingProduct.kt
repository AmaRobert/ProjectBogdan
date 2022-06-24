package com.example.projectbogdan.data.model

import com.example.projectbogdan.data.Constants
import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class ShoppingProduct(
    @DocumentId
    var documentId: String = "",
    var name: String = "",
    val category: String = "",
    var mentions: String = "",
    var quantity: String = "",
    var rating: Double = 0.toDouble(),
    var hasDate: Boolean = false
):Serializable