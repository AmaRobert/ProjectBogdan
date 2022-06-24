package com.example.projectbogdan.ui.shopingList

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectbogdan.R
import com.example.projectbogdan.data.model.ShoppingProduct


class ShoppingItemViewHolder(
    private val clickListener: ClickListener,
    view: View
) : RecyclerView.ViewHolder(view){

    private val textViewProductName = view.findViewById<TextView>(R.id.textViewProductName)
    private val imageButtonAdd = view.findViewById<ImageButton>(R.id.imageButtonAdd)
    private val imageButtonDelete = view.findViewById<ImageButton>(R.id.imageButtonDelete)
    private val checkBox = view.findViewById<CheckBox>(R.id.checkBoxShop)


    fun bind(item: ShoppingProduct){
        textViewProductName.text = item.name
        imageButtonAdd.setOnClickListener{
            clickListener.onInfoClicked(item)
        }
        imageButtonDelete.setOnClickListener{
            clickListener.onDeleteItemClicked(item)
        }
        checkBox.setOnCheckedChangeListener { compoundButton, b ->
            clickListener.onAddItemToStorageListClicked(item, b)
            if(b){
                textViewProductName.paintFlags = textViewProductName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }else{
                textViewProductName.paintFlags = textViewProductName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    interface ClickListener{
        fun onInfoClicked(item: ShoppingProduct)
        fun onDeleteItemClicked(item: ShoppingProduct)
        fun onAddItemToStorageListClicked(item: ShoppingProduct, isChecked: Boolean)
    }

    companion object{
        fun create(
            clickListener: ClickListener,
            parent: ViewGroup
        ): ShoppingItemViewHolder = ShoppingItemViewHolder(
            clickListener = clickListener,
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_shopping_list,
                parent,
                false
            )
        )
    }
}