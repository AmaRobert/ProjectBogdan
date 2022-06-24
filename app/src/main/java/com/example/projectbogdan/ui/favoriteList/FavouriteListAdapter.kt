package com.example.projectbogdan.ui.favoriteList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.projectbogdan.data.model.FavouriteProduct

class FavouriteListAdapter(
    private val clickListener: FavouriteItemViewHolder.ClickListener
) : ListAdapter<FavouriteProduct, FavouriteItemViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteItemViewHolder
    = FavouriteItemViewHolder.create(clickListener, parent)

    override fun onBindViewHolder(holder: FavouriteItemViewHolder, position: Int)
    = holder.bind(getItem(position))

    companion object{
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<FavouriteProduct>(){
            override fun areItemsTheSame(
                oldItem: FavouriteProduct,
                newItem: FavouriteProduct
            ) = oldItem.documentId == newItem.documentId

            override fun areContentsTheSame(
                oldItem: FavouriteProduct,
                newItem: FavouriteProduct
            ) = oldItem == newItem
        }
    }
}