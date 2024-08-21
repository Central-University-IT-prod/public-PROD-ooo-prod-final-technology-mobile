package com.prodtechnology.teammatching.ui.main.swap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.adapters.TeamsAdapter
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.databinding.FragmentTeamsBinding
import com.prodtechnology.teammatching.utils.statuses.TeamsStatus

class TeamsFragment : Fragment() {
    private var _binding: FragmentTeamsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TeamsAdapter

    private lateinit var viewModel: TeamsMembersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this
        )[TeamsMembersViewModel::class.java]

        viewModel.teamsStatus.observe(viewLifecycleOwner){
            when(it){
                is TeamsStatus.Succeed -> {onGetTeamsSucceed(it.data)}
                is TeamsStatus.Failed -> {onGetTeamsFailed(it.error)}
            }
        }
        viewModel.getTeams(AppPrefs.getToken()!!, AppPrefs.getEventId()!!)
        return binding.root
    }

    private fun onGetTeamsFailed(error: String) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onGetTeamsSucceed(data: List<TeamInfo>) {
        initRcView(data)
    }

    private fun initRcView(list: List<TeamInfo>) = with(binding){
        rcTeam.layoutManager = LinearLayoutManager(activity)
        adapter = TeamsAdapter(object : TeamsAdapter.OnItemClickListener{
            override fun onItemClick(item: TeamInfo) {
                val b = Bundle()
                b.putInt("TeamId", item.id)
                AppPrefs.setTeamId(item.id)
                findNavController().navigate(R.id.action_mainScreenFragment_to_teamFragment, b)
            }

        })
        rcTeam.adapter = adapter
        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = TeamsFragment()
    }
}