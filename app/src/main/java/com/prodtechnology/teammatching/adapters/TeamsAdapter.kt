package com.prodtechnology.teammatching.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.databinding.ItemTeamsBinding
import kotlin.math.min

class TeamsAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<TeamInfo, TeamsAdapter.Holder>(Comparator()) {
    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTeamsBinding.bind(view)
        fun bind(item: TeamInfo) = with(binding) {
            nameTeam.text = item.title
            val allSkills = mutableListOf<String>()
            for(user in item.users){
                for(skill in user.skills){
                    allSkills.add(skill.title)
                }
            }
            stackTeam.text = allSkills.subList(0, min(4, allSkills.size - 1)).joinToString(separator = " • ")
        }
    }

    class Comparator : DiffUtil.ItemCallback<TeamInfo>() {
        override fun areItemsTheSame(oldItem: TeamInfo, newItem: TeamInfo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TeamInfo, newItem: TeamInfo): Boolean {
            return oldItem.title == newItem.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_teams, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.cvTeamRoot.setOnClickListener {
            onItemClickListener.onItemClick(getItem(position))
        }
    }

    interface OnItemClickListener{
        fun onItemClick(item: TeamInfo)
    }
}