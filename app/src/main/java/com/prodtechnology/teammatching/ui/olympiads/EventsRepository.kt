package com.prodtechnology.teammatching.ui.olympiads

import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.events.EventResponse
import com.prodtechnology.teammatching.data.remote.events.EventsService
import com.prodtechnology.teammatching.data.remote.profile.ProfileResponse
import com.prodtechnology.teammatching.data.remote.profile.ProfileService
import retrofit2.Call

class EventsRepository {
    private val client = RetrofitClient.getEventsClient()
    private val service = client.create(EventsService::class.java)

    fun getEventsCall(token: String): Call<List<EventResponse>> {
        return service.getEvents(token)
    }
}