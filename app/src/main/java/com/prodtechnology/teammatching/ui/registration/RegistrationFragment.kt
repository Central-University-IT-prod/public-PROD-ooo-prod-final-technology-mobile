package com.prodtechnology.teammatching.ui.registration

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.databinding.FragmentRegistrationBinding
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.account.SkillsResponse
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.models.UserRequest
import com.prodtechnology.teammatching.ui.olympiads.EventsActivity
import com.prodtechnology.teammatching.utils.statuses.SkillsStatus
import com.prodtechnology.teammatchingadmin.utils.status.AuthStatus

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegistrationViewModel
    private var itemsMap = mutableMapOf<Int, String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            RegistrationViewModelFactory(requireContext())
        )[RegistrationViewModel::class.java]

        viewModel.status.observe(viewLifecycleOwner){
            binding.llRegistrationLoading.visibility = View.GONE
            when(it){
                is AuthStatus.Succeed -> { onAuthSucceed(it.token) }
                is AuthStatus.Failed -> { onAuthFailed(it.error) }
            }
        }
        viewModel.getSkills()
        viewModel.status_skills.observe(viewLifecycleOwner){
            when(it){
                is SkillsStatus.Succeed -> { onSkillsSucceed(it.data) }
                is SkillsStatus.Failed -> { onAuthFailed(it.error) }
            }
        }

        binding.spinnerProfession.adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_profession,
            arrayOf("frontend", "backend", "mobile")
        )

        binding.etRegistrationTelegram.addTextChangedListener(
            onTextChanged = { text, st, bef, count ->
                if(text != null) {
                    if (!text.startsWith("@")) {
                        binding.etRegistrationTelegram.text =
                            Editable.Factory.getInstance().newEditable("@${text}")
                        binding.etRegistrationTelegram.setSelection(binding.etRegistrationTelegram.text.length)
                    }
                }
            }
        )
        binding.btnContiniu.setOnClickListener {
            binding.first.isVisible = false
            binding.second.isVisible = true
        }
        binding.btnBack.setOnClickListener {
            binding.second.isVisible = false
            binding.first.isVisible = true
        }
        val selectedIdsList = mutableListOf<Int>()

        binding.tvAddSkill.setOnClickListener {
            if(binding.etStille.text.isEmpty()){
                return@setOnClickListener
            }
            if(binding.etStille.text.toString() !in itemsMap.values){
                binding.editSkille.error = getString(R.string.error_not_in_list)
            }else{
                binding.ivDeleteSkill.visibility = View.VISIBLE
                val selectedId = itemsMap.entries.find { it.value == binding.etStille.text.toString() }?.key
                selectedId?.let { selectedIdsList.add(it) }
                if(selectedIdsList.size == 5){
                    binding.etStille.isEnabled = false
                }
                binding.tvSkillsList.text = selectedIdsList.map { itemsMap[it] }.joinToString()
                binding.etStille.text = Editable.Factory.getInstance().newEditable("")
            }
        }

        binding.ivDeleteSkill.setOnClickListener {
            selectedIdsList.removeLast()
            binding.tvSkillsList.text = selectedIdsList.map { itemsMap[it] }.joinToString()
            if(selectedIdsList.size == 0){
                binding.ivDeleteSkill.visibility = View.GONE
            }
        }

        binding.btnSignUp.setOnClickListener {
            binding.inputLayoutRegistrationEmail.isErrorEnabled = false
            binding.inputLayoutRegistrationPassword.isErrorEnabled = false
            binding.inputLayoutRegistrationPasswordRepeat.isErrorEnabled = false
            binding.inputLayoutRegistrationTelegram.isErrorEnabled = false
            binding.inputLayoutRegistrationFullname.isErrorEnabled = false

            var flag = true
            if(!Patterns.EMAIL_ADDRESS.matcher(binding.etRegistrationEmail.text).matches()){
                binding.inputLayoutRegistrationEmail.error = getString(R.string.error_bad_email)
                flag = false
            }
            if(binding.etRegistrationPassword.text.length < 6){
                flag = false
                binding.inputLayoutRegistrationPassword.error = getString(R.string.error_short_password)
            }
            if(binding.etRegistrationFullname.text.isEmpty()){
                flag = false
                binding.inputLayoutRegistrationFullname.error = getString(R.string.error_empty_field)
            }
            if(binding.etRegistrationTelegram.text.isEmpty()){
                flag = false
                binding.inputLayoutRegistrationTelegram.error = getString(R.string.error_empty_field)
            }
            if(binding.etRegistrationPassword.text.toString() != binding.etRegistrationPasswordRepeat.text.toString()){
                flag = false
                binding.inputLayoutRegistrationPasswordRepeat.error = getString(R.string.error_password_not_match)
            }
            if(binding.etAge.text.isEmpty()){
                flag = false
                binding.editAge.error = getString(R.string.error_empty_field)
            }
            if(binding.etBio.text.isEmpty()){
                flag = false
                binding.editBio.error = getString(R.string.error_empty_field)
            }
            if(selectedIdsList.size == 0){
                flag = false
                binding.editSkille.error = getString(R.string.error_empty_field)
            }
            if(flag){
                binding.llRegistrationLoading.setOnClickListener {}
                binding.llRegistrationLoading.visibility = View.VISIBLE
                viewModel.signUpUser(
                    UserRequest(
                        binding.etRegistrationEmail.text.toString(),
                        binding.etRegistrationFullname.text.toString(),
                        binding.etRegistrationPassword.text.toString(),
                        binding.etRegistrationTelegram.text.toString(),
                        selectedIdsList,
                        binding.etAge.text.toString().toInt(),
                        binding.spinnerProfession.selectedItem.toString(),
                        binding.etBio.text.toString()
                    )
                )
            }
        }

        binding.tvToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        return binding.root
    }


    private fun onAuthSucceed(token: String) {
        AppPrefs.setToken(token)
        AppPrefs.setEmail(binding.etRegistrationEmail.text.toString())
        startActivity(Intent(requireContext(), EventsActivity::class.java))
        requireActivity().finish()
    }
    private fun onSkillsSucceed(data: List<SkillsResponse>) {
        for(item in data){
            itemsMap[item.id] = item.title
        }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, itemsMap.values.toList())
        binding.etStille.setAdapter(adapter)

    }

    private fun onAuthFailed(error: String){
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}