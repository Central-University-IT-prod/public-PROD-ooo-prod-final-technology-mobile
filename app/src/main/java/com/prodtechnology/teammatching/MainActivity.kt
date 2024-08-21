package com.prodtechnology.teammatching

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.account.AccountService
import com.prodtechnology.teammatching.data.remote.models.IsInTeamResponse
import com.prodtechnology.teammatching.databinding.ActivityMainBinding
import com.prodtechnology.teammatching.ui.profil.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setOnMenuItemClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            return@setOnMenuItemClickListener true
        }
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment).navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}