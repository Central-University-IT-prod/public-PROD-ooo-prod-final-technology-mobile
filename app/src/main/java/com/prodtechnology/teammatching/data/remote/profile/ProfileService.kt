package com.prodtechnology.teammatching.data.remote.profile

import com.prodtechnology.teammatching.data.remote.events.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileService {
    @GET("/api/users/me")
    fun getProfile(
        @Header("Authorization") token: String
    ) : Call<ProfileResponse>
}