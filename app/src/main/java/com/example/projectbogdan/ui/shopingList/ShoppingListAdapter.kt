package com.example.projectbogdan.ui.shopingList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.projectbogdan.data.model.ShoppingProduct


class ShoppingListAdapter(
    private val clickListener: ShoppingItemViewHolder.ClickListener
) : ListAdapter<ShoppingProduct, ShoppingItemViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder
            = ShoppingItemViewHolder.create(clickListener, parent)

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int)
            = holder.bind(getItem(position))

    companion object{
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ShoppingProduct>(){
            override fun areItemsTheSame(
                oldItem: ShoppingProduct,
                newItem: ShoppingProduct
            ) = oldItem.documentId == newItem.documentId

            override fun areContentsTheSame(
                oldItem: ShoppingProduct,
                newItem: ShoppingProduct
            ) = oldItem == newItem
        }
    }
}