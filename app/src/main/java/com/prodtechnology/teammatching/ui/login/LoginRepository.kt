package com.prodtechnology.teammatching.ui.login

import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.account.AuthResponse
import com.prodtechnology.teammatching.data.remote.account.AccountService
import com.prodtechnology.teammatching.data.remote.models.LoginUser
import com.prodtechnology.teammatching.data.remote.models.User
import retrofit2.Call

class LoginRepository {
    private val client = RetrofitClient.getAccountClient()
    private val service = client.create(AccountService::class.java)



    fun authUser(user: LoginUser): Call<AuthResponse> {
        return service.authUser(user)
    }

    fun authUserToken(token: String): Call<User>{
        return service.getUserByToken(token)
    }
}