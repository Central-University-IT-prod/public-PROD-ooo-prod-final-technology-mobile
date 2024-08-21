package com.prodtechnology.teammatching.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prodtechnology.teammatching.R

class SkillsAdapter(private val skills: MutableList<String>) :
    RecyclerView.Adapter<SkillsAdapter.SkillsViewHolder>() {

    inner class SkillsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skillTextView: TextView = itemView.findViewById(R.id.editSkils)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skils, parent, false)
        return SkillsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        val skill = skills[position]
        holder.skillTextView.text = skill
    }

    fun addSkill(skill: String) {
        skills.add(skill)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return skills.size
    }
}