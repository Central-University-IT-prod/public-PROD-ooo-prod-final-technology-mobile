package com.prodtechnology.teammatching.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.databinding.FragmentMainScreenBinding
import com.prodtechnology.teammatching.ui.main.swap.MembersFragment
import com.prodtechnology.teammatching.ui.main.swap.TeamsFragment
import com.prodtechnology.teammatching.adapters.SwapAdapter

class MainScreenFragment : Fragment() {
    private val listSwap = listOf(
        TeamsFragment.newInstance(),
        MembersFragment.newInstance()
    )
    private lateinit var listTabLayout: List<String>
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        listTabLayout = listOf(
            requireContext().getString(R.string.teams),
            requireContext().getString(R.string.participants)
        )
        initswap()
        return binding.root
    }

    private fun initswap() = with(binding){
        val adapter = SwapAdapter(activity as FragmentActivity, listSwap)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
            tab, pos -> tab.text = listTabLayout[pos]
        }.attach()
    }
}