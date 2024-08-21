package com.prodtechnology.teammatching.ui.profil

import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.profile.ProfileResponse
import com.prodtechnology.teammatching.data.remote.profile.ProfileService
import retrofit2.Call

class ProfileRepository {
    private val client = RetrofitClient.getProfileClient()
    private val service = client.create(ProfileService::class.java)

    fun getProfileCall(token: String): Call<ProfileResponse> {
        return service.getProfile(token)
    }
}