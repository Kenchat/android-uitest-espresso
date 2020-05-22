package com.kbtg.android.espresso.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.kbtg.android.espresso.R

class ListHomeAdapter(
    private var itemList: List<String>,
    private var onItemClick: ((item: String, position: Int) -> Unit)
) : RecyclerView.Adapter<ListHomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.btnData.text = "Item ${itemList.get(position)}"
        holder.btnData.setOnClickListener {
            onItemClick.invoke(
                itemList[position],
                position
            )
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnData: Button = itemView.findViewById(R.id.btnItem) as Button
    }
}