package com.prodtechnology.teammatching.ui.team

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
import com.prodtechnology.teammatching.adapters.MembersAdapter
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.models.IsInTeamResponse
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.databinding.FragmentTeamBinding
import com.prodtechnology.teammatching.utils.statuses.IsInTeamStatus
import com.prodtechnology.teammatching.utils.statuses.RequestStatus
import com.prodtechnology.teammatching.utils.statuses.TeamStatus

class TeamFragment : Fragment() {
    private var _binding: FragmentTeamBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TeamViewModel

    private var teamId: Int? = AppPrefs.getTeamId()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(
            this
        )[TeamViewModel::class.java]
        viewModel.teamStatus.observe(viewLifecycleOwner){
            when(it){
                is TeamStatus.Succeed -> {onGetTeamSucceed(it.team)}
                is TeamStatus.Failed -> {onGetTeamFailed(it.error)}
            }
        }
        viewModel.isInTeam.observe(viewLifecycleOwner){
            when(it){
                is IsInTeamStatus.Succeed -> {onInTeam(it.teamId)}
                is IsInTeamStatus.Failed -> {onNotInTeam(it.error)}
            }
        }
        viewModel.requestStatus.observe(viewLifecycleOwner){
            when(it){
                is RequestStatus.Succeed -> {onRequestSucceed()}
                is RequestStatus.Failed -> {onRequestFailed(it.error)}
            }
        }
        if(arguments == null){
            binding.btnSendRequest.visibility = View.GONE
            if(AppPrefs.getTeamId() == null) {
                viewModel.getIsInTeam(AppPrefs.getToken()!!, AppPrefs.getEventId()!!)
            }else{
                viewModel.getTeamById(AppPrefs.getToken()!!, AppPrefs.getTeamId()!!)
            }
        }else{
            viewModel.getTeamById(AppPrefs.getToken()!!, AppPrefs.getTeamId()!!)
        }
        binding.btnCreateTeam.setOnClickListener {
            findNavController().navigate(R.id.action_nav_my_team_to_createTeamFragment)
        }
        binding.btnSendRequest.setOnClickListener {
            viewModel.sendRequest(AppPrefs.getToken()!!, AppPrefs.getTeamId()!!)
        }
        return binding.root
    }

    private fun onRequestSucceed() {
        Toast.makeText(requireContext(), "Успешно!", Toast.LENGTH_SHORT).show()
    }

    private fun onRequestFailed(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun onNotInTeam(error: String) {
        binding.btnCreateTeam.visibility = View.VISIBLE
        binding.layoutTeamMain.visibility = View.GONE
    }

    private fun onInTeam(teamId: Int) {
        AppPrefs.setTeamId(teamId)
        binding.btnCreateTeam.visibility = View.VISIBLE
        binding.layoutTeamMain.visibility = View.GONE
        viewModel.getTeamById(AppPrefs.getToken()!!, teamId)
    }

    private fun onGetTeamSucceed(team: TeamInfo) {
        var mobile = 0
        var frontend = 0
        var backend = 0
        val stack = mutableSetOf<String>()
        for(user in team.users){
            when(user.profession){
                "mobile" -> {mobile++}
                "frontend" -> {frontend++}
                else -> {backend++}
            }
            stack.addAll(user.skills.map { it.title })
        }

        binding.aboutTeam.text = team.description
        binding.frontendUsers.text = frontend.toString()
        binding.backendUsers.text = backend.toString()
        binding.mobileUsers.text = mobile.toString()
        binding.stackTeam.text = stack.joinToString()

        val adapter = MembersAdapter(false)
        adapter.submitList(team.users)
        binding.rcTeam.adapter = adapter
        binding.rcTeam.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun onGetTeamFailed(error: String) {

    }
}