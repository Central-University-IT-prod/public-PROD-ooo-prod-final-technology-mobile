package com.prodtechnology.teammatching.ui.create_team

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.models.TeamRequest
import com.prodtechnology.teammatching.databinding.FragmentCreateTeamBinding
import com.prodtechnology.teammatching.utils.statuses.CreateTeamStatus

class CreateTeamFragment : Fragment() {
    private var _binding: FragmentCreateTeamBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CreateTeamViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTeamBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this
        )[CreateTeamViewModel::class.java]
        viewModel.status.observe(viewLifecycleOwner){
            when(it){
                is CreateTeamStatus.Succeed -> {onCreateSucceed(it.id)}
                is CreateTeamStatus.Failed -> {onCreateFailed(it.error)}
            }
        }
        binding.btnCreate.setOnClickListener {
            var flag = true
            if(binding.etCreateTeamTitle.text.isEmpty()){
                binding.inputLayoutCreateTeamTitle.error = getString(R.string.error_empty_field)
                flag = false
            }
            if(binding.etCreateTeamDescription.text.isEmpty()){
                binding.inputLayoutCreateTeamDescription.error = getString(R.string.error_empty_field)
                flag = false
            }
            if(flag){
                viewModel.addTeam(AppPrefs.getToken()!!, TeamRequest(
                    binding.etCreateTeamTitle.text.toString(),
                    binding.etCreateTeamDescription.text.toString(),
                    AppPrefs.getEventId()!!
                ))
            }
        }
        return binding.root
    }

    private fun onCreateSucceed(id: Int) {
        AppPrefs.setTeamId(id)
        val b = Bundle()
        b.putInt("TeamId", id)
        findNavController().navigate(R.id.action_createTeamFragment_to_nav_my_team, b)
    }

    private fun onCreateFailed(error: String) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }
}