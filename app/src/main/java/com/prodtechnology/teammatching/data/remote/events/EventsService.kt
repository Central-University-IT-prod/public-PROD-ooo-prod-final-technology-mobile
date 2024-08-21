package com.prodtechnology.teammatching.data.remote.events

import com.prodtechnology.teammatching.data.remote.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface EventsService {
    @GET("/api/user-events")
    fun getEvents(
        @Header("Authorization") token: String
    ) : Call<List<EventResponse>>
}