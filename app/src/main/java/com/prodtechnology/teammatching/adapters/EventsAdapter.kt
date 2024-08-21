package com.prodtechnology.teammatching.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.data.remote.events.EventResponse
import com.prodtechnology.teammatching.databinding.ItemOlympiadBinding


interface OlympiadItemClickListener {
    fun onItemClick(eventResponse: EventResponse)
}

class EventsAdapter(private val clickListener: OlympiadItemClickListener): ListAdapter<EventResponse, EventsAdapter.Holder>(Comparator()) {
    class Holder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemOlympiadBinding.bind(view)
        fun bind(item: EventResponse) = with(binding){
            nameOlympiad.text = item.title
            dateOlympiad.text = item.deadline
        }
    }
    class Comparator : DiffUtil.ItemCallback<EventResponse>(){
        override fun areItemsTheSame(oldItem: EventResponse, newItem: EventResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EventResponse, newItem: EventResponse): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_olympiad, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val eventResponse = getItem(position)
        holder.bind(eventResponse)
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(eventResponse)
        }
    }
}