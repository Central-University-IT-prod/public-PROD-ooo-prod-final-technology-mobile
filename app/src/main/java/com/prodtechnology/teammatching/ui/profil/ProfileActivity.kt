package com.prodtechnology.teammatching.ui.profil

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.prodtechnology.teammatching.AccountActivity
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.profile.ProfileResponse
import com.prodtechnology.teammatching.databinding.ActivityProfileBinding
import com.prodtechnology.teammatching.utils.statuses.ProfileStatuse

class ProfileActivity : AppCompatActivity() {

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this
        )[ProfileViewModel::class.java]
        viewModel.getProfile(AppPrefs.getToken()!!)
        viewModel.profileStatus.observe(this){
            when(it){
                is ProfileStatuse.Succeed -> {onGetTeamsSucceed(it.data)}
                is ProfileStatuse.Failed -> {onGetTeamsFailed(it.error)}
            }
        }
        binding.btnLogOut.setOnClickListener {
            AppPrefs.setToken(null)
            AppPrefs.setEmail("")
            startActivity(Intent(this, AccountActivity::class.java))
            finish()
        }

    }

    private fun onGetTeamsFailed(error: String) {
        Toast.makeText(
            this,
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onGetTeamsSucceed(data: ProfileResponse) {
        init(data)
    }

    private fun init(data: ProfileResponse) = with(binding) {
        editName.text = data.fullName
        editEmail.text = data.emailProfile
        editTelegram.text = data.telegram
        val lastIndex = data.stake.size - 1
        var stack = ""
        for(item in 0..lastIndex){
            if(item != lastIndex) {
                stack += "${data.stake[item].title} • "
            } else {
                stack += data.stake[item].title
            }
        }
        editSkils.text = stack
        editAbout.text = data.about
        edProfession.text = data.profession
        edAge.text = data.age.toString()
    }
}