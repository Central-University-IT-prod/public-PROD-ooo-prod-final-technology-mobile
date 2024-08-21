package com.prodtechnology.teammatching.ui.main.swap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prodtechnology.teammatching.adapters.MembersAdapter
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.models.UserInfo
import com.prodtechnology.teammatching.databinding.FragmentParticipantsBinding
import com.prodtechnology.teammatching.utils.statuses.MembersStatus

class MembersFragment : Fragment() {
    private var _binding: FragmentParticipantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MembersAdapter
    private lateinit var viewModel: TeamsMembersViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParticipantsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this
        )[TeamsMembersViewModel::class.java]

        viewModel.membersStatus.observe(viewLifecycleOwner){
            when(it){
                is MembersStatus.Succeed -> {onGetMembersSucceed(it.data)}
                is MembersStatus.Failed -> {onGetMembersFailed(it.error)}
            }
        }
        viewModel.getMembers(AppPrefs.getToken()!!, AppPrefs.getEventId()!!)
        return binding.root
    }

    private fun onGetMembersFailed(error: String) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onGetMembersSucceed(data: List<User>) {
        initRcView(data)
    }

    private fun initRcView(list: List<User>) = with(binding){
        rcParicipants.layoutManager = LinearLayoutManager(activity)
        adapter = MembersAdapter(true)
        rcParicipants.adapter = adapter
        adapter.submitList(list)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MembersFragment()
    }
}