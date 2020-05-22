package com.kbtg.android.espresso.ui.countrydetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kbtg.android.espresso.R
import com.kbtg.android.espresso.ui.countrydetail.model.CountryDetailResponseItem

class NationDetailAdapter(
        private var itemList: List<CountryDetailResponseItem>
) :
        RecyclerView.Adapter<NationDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_for_country_detail_list, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvConfirmed.text = itemList[position].Confirmed.toString()
        holder.tvRecovered.text = itemList[position].Recovered.toString()
        holder.tvDeaths.text = itemList[position].Deaths.toString()

        holder.tvDate.text = itemList[position].Date
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvConfirmed: TextView = itemView.findViewById(R.id.tvConfirmValue)
        val tvRecovered: TextView = itemView.findViewById(R.id.tvRecoveredValue)
        val tvDeaths: TextView = itemView.findViewById(R.id.tvDeathValue)
    }
}