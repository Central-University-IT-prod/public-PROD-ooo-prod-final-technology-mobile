package com.prodtechnology.teammatching.data.remote.account

import com.prodtechnology.teammatching.data.remote.models.IsInTeamResponse
import com.prodtechnology.teammatching.data.remote.models.LoginUser
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.models.UserRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AccountService {
    @GET("/api/skills")
    fun getSkills() : Call<List<SkillsResponse>>

    @POST("api/users/login")
    fun authUser(
        @Body user: LoginUser
    ) : Call<AuthResponse>

    @GET("api/users/me")
    fun getUserByToken(
        @Header("Authorization") token: String
    ) : Call<User>

    @POST("api/users")
    fun signUpUser(
        @Body user: UserRequest
    ) : Call<AuthResponse>
}