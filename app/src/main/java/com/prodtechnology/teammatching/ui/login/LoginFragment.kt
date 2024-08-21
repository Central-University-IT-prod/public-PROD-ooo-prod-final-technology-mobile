package com.prodtechnology.teammatching.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.databinding.FragmentLoginBinding
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.models.LoginUser
import com.prodtechnology.teammatching.ui.olympiads.EventsActivity
import com.prodtechnology.teammatchingadmin.utils.status.AuthStatus

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    private var email = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(requireContext())
        )[LoginViewModel::class.java]

        viewModel.status.observe(viewLifecycleOwner){
            binding.llLoginLoading.visibility = View.GONE
            when(it){
                is AuthStatus.Succeed -> { onLoginSucceed(it.token, it.email) }
                is AuthStatus.Failed -> { onLoginFailed(it.error) }
            }
        }

        if(AppPrefs.getToken() != null){
            binding.llLoginLoading.setOnClickListener {}
            binding.llLoginLoading.visibility = View.VISIBLE
            viewModel.loginUserToken(AppPrefs.getToken()!!)
        }

        binding.btnSignIn.setOnClickListener {
            binding.inputLayoutAuthEmail.isErrorEnabled = false
            binding.inputLayoutAuthPassword.isErrorEnabled = false

            if(!Patterns.EMAIL_ADDRESS.matcher(binding.etAuthEmail.text).matches()){
                binding.inputLayoutAuthEmail.error = getString(R.string.error_bad_email)
            }
            if(binding.etAuthPassword.text.length < 6){
                binding.inputLayoutAuthPassword.error = getString(R.string.error_short_password)
            }
            if(!binding.inputLayoutAuthEmail.isErrorEnabled && !binding.inputLayoutAuthPassword.isErrorEnabled){
                binding.llLoginLoading.visibility = View.VISIBLE
                viewModel.loginUser(
                    LoginUser(
                    binding.etAuthEmail.text.toString(),
                    binding.etAuthPassword.text.toString()
                )
                )
            }
        }

        binding.tvToRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
        return binding.root
    }

    private fun onLoginFailed(error: String) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_SHORT
        ).show()
        AppPrefs.setToken(null)
    }

    private fun onLoginSucceed(token: String, email: String) {
        AppPrefs.setToken(token)
        AppPrefs.setEmail(email)
        startActivity(Intent(requireContext(), EventsActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}