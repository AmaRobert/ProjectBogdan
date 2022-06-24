package com.example.projectbogdan.ui.favoriteList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectbogdan.R
import com.example.projectbogdan.data.model.FavouriteProduct

class FavouriteItemViewHolder(
    private val clickListener: ClickListener,
    view: View
) : RecyclerView.ViewHolder(view){

    private val textViewProductName = view.findViewById<TextView>(R.id.textViewProductName)
    private val imageButtonAdd = view.findViewById<ImageButton>(R.id.imageButtonAdd)
    private val imageButtonDelete = view.findViewById<ImageButton>(R.id.imageButtonDelete)


    fun bind(item: FavouriteProduct){
        textViewProductName.text = item.name
        imageButtonAdd.setOnClickListener{
            clickListener.onAddItemToShoppingListClicked(item, imageButtonAdd.isEnabled)
        }
        imageButtonDelete.setOnClickListener{
            clickListener.onDeleteItemClicked(item)
        }
    }




    interface ClickListener{
        fun onItemClicked(item: FavouriteProduct)
        fun onDeleteItemClicked(item: FavouriteProduct)
        fun onAddItemToShoppingListClicked(item: FavouriteProduct, isAdded: Boolean)
    }

    companion object{
        fun create(
            clickListener: ClickListener,
            parent: ViewGroup
        ): FavouriteItemViewHolder = FavouriteItemViewHolder(
            clickListener = clickListener,
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_favourite_list,
                parent,
                false
            )
        )
    }
}