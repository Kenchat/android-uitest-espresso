package com.kbtg.android.espresso.page3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.page3.model.Country

class SummaryDataAdapter(
        private var itemList: List<Country>?,
        private var onItemClick: ((item: String) -> Unit)? = null
) :
        RecyclerView.Adapter<SummaryDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_for_summary_covid_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvContryName.text = itemList?.get(position)?.Country ?: ""
        holder.tvTotalConfirm.text = itemList?.get(position)?.TotalConfirmed.toString()
        holder.container.setOnClickListener {
            onItemClick?.invoke(
                    itemList?.get(position)?.Country.toString()
            )
        }
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    fun setItemList(itemList: List<Country>?) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContryName: TextView = itemView.findViewById(R.id.tvCountryName)
        val tvTotalConfirm: TextView = itemView.findViewById(R.id.tvTotalConfirm)
        val container: ConstraintLayout = itemView.findViewById(R.id.summaryItemContainer)
    }
}