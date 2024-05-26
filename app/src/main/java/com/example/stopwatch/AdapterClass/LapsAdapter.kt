package com.example.stopwatch.AdapterClass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.stopwatch.R

class LapsAdapter(context: Context, items: ArrayList<String>) :
    ArrayAdapter<String>(context, R.layout.lap_item_list, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.lap_item_list, parent, false)
        }

        val item = getItem(position)
        val itemText = itemView!!.findViewById<TextView>(R.id.lap_text)
        itemText.text = item

        // Example: Accessing other elements in your custom layout
        // val itemIcon = itemView.findViewById<ImageView>(R.id.item_icon)
        // Set icon based on item data

        return itemView
    }
}
