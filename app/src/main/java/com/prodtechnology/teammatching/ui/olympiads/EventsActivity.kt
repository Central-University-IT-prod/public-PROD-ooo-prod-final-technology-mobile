package com.prodtechnology.teammatching.ui.olympiads

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prodtechnology.teammatching.AccountActivity
import com.prodtechnology.teammatching.MainActivity
import com.prodtechnology.teammatching.adapters.OlympiadItemClickListener
import com.prodtechnology.teammatching.adapters.EventsAdapter
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.events.EventResponse
import com.prodtechnology.teammatching.databinding.ActivityOlympiadsBinding
import com.prodtechnology.teammatching.ui.profil.ProfileActivity
import com.prodtechnology.teammatching.utils.statuses.EventsStatus

class EventsActivity : AppCompatActivity() {
    private var _binding: ActivityOlympiadsBinding? = null
    private lateinit var viewModel: EventsViewModel
    private val binding get() = _binding!!
    private lateinit var adapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOlympiadsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this
        )[EventsViewModel::class.java]
        viewModel.eventsStatus.observe(this){
            binding.eventsRefreshLayout.isRefreshing = false
            when(it){
                is EventsStatus.Succeed -> {onGetEventsSucceed(it.data)}
                is EventsStatus.Failed -> {onGetEventsFailed(it.error)}
            }
        }
        binding.toolbar2.setOnMenuItemClickListener{
            val i = Intent(this, ProfileActivity::class.java)
            startActivity(i)
            return@setOnMenuItemClickListener true
        }
        viewModel.getEvents(AppPrefs.getToken()!!)
        binding.eventsRefreshLayout.setOnRefreshListener {
            viewModel.getEvents(AppPrefs.getToken()!!)
        }
    }

    private fun onGetEventsFailed(error: String) {
        Toast.makeText(
            this,
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onGetEventsSucceed(data: List<EventResponse>) {
        initRcView(data)
    }
    private fun initRcView(list: List<EventResponse>) = with(binding){
        rcOlymp.layoutManager = LinearLayoutManager(this@EventsActivity)
        adapter = EventsAdapter(object : OlympiadItemClickListener {
            override fun onItemClick(eventResponse: EventResponse) {
                val i = Intent(this@EventsActivity, MainActivity::class.java)
                AppPrefs.setEventId(eventResponse.id)
                startActivity(i)
            }
        })
        rcOlymp.adapter = adapter
        adapter.submitList(list)
    }
}