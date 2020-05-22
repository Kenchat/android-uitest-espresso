package com.kbtg.android.espresso.ui.nationlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.nationlist.model.Country

class NationListDataAdapter(
        private var itemList: List<Country>,
        private var onItemClick: ((item: String) -> Unit)
) :
        RecyclerView.Adapter<NationListDataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_for_summary_covid_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCountryName.text = itemList[position].Country
        holder.tvTotalConfirm.text = itemList[position].TotalConfirmed.toString()
        holder.container.setOnClickListener {
            onItemClick.invoke(
                    itemList[position].Country
            )
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCountryName: TextView = itemView.findViewById(R.id.tvCountryName)
        val tvTotalConfirm: TextView = itemView.findViewById(R.id.tvTotalConfirm)
        val container: ConstraintLayout = itemView.findViewById(R.id.summaryItemContainer)
    }
}