package com.prodtechnology.teammatching.ui.registration

import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.account.AuthResponse
import com.prodtechnology.teammatching.data.remote.account.AccountService
import com.prodtechnology.teammatching.data.remote.account.SkillsResponse

import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.models.UserRequest
import retrofit2.Call

class RegistrationRepository {
    private val client = RetrofitClient.getAccountClient()
    private val service = client.create(AccountService::class.java)
    private val client_skills = RetrofitClient.getSkillsClient()
    private val service_skills = client_skills.create(AccountService::class.java)

    fun signUpUser(user: UserRequest) : Call<AuthResponse>{
        return service.signUpUser(user)
    }

    fun getSkills() : Call<List<SkillsResponse>>{
        return service_skills.getSkills()
    }
}