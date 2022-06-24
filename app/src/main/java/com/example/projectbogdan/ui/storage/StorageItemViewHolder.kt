package com.example.projectbogdan.ui.storage

import android.app.DatePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectbogdan.R
import com.example.projectbogdan.data.model.StorageProduct
import java.text.SimpleDateFormat
import java.util.*

class StorageItemViewHolder(
    private val clickListener: ClickListener,
    view: View
) : RecyclerView.ViewHolder(view){

    private val textViewProductName = view.findViewById<TextView>(R.id.textViewProductName)
    private val textViewProductCategory = view.findViewById<TextView>(R.id.textViewProductCategory)
    private val timePickerButton = view.findViewById<ImageButton>(R.id.timePickerButton)
    private val imageButtonDelete = view.findViewById<ImageButton>(R.id.imageButtonDelete)
    private val scanExpirationDateButton = view.findViewById<ImageButton>(R.id.scanExpirationDateButton)

    fun bind(item: StorageProduct){
        textViewProductName.text = item.name
        textViewProductCategory.text = item.expirationDate
        val calendar = Calendar.getInstance()
        val dateSetListener : DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                textViewProductCategory.text =
                    SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(calendar.time)
                item.expirationDate = textViewProductCategory.text as String
                clickListener.onTimePickerClicked(item)
            }
        val datePickerDialog = DatePickerDialog(
            textViewProductName.context,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        timePickerButton.setOnClickListener{
            datePickerDialog.show()
        }
        imageButtonDelete.setOnClickListener{
            clickListener.onDeleteItemClicked(item)
        }
        scanExpirationDateButton.setOnClickListener {
            clickListener.onScanExpirationDateClicked(item)
        }
    }


    interface ClickListener{
        fun onTimePickerClicked(item: StorageProduct)
        fun onDeleteItemClicked(item: StorageProduct)
        fun onScanExpirationDateClicked(item: StorageProduct)
    }

    companion object{
        fun create(
            clickListener: ClickListener,
            parent: ViewGroup
        ): StorageItemViewHolder = StorageItemViewHolder(
            clickListener = clickListener,
            view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_storage_list,
                parent,
                false
            )
        )
    }
}