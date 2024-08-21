package com.prodtechnology.teammatching.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.prodtechnology.teammatching.data.remote.account.AuthDeserializer
import com.prodtechnology.teammatching.data.remote.account.AuthResponse
import com.prodtechnology.teammatching.utils.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var teamsMembersClient: Retrofit? = null
    private var accountClient: Retrofit? = null
    private var eventClient: Retrofit? = null
    private var profileClient: Retrofit? = null
    private var skillsClient: Retrofit? = null
    fun getTeamsMembersClient(): Retrofit{
        return teamsMembersClient ?: synchronized(this){
            teamsMembersClient = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            teamsMembersClient!!
        }
    }

    fun getAccountClient(): Retrofit{
        return accountClient ?: synchronized(this){
            accountClient = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().registerTypeAdapter(object : TypeToken<AuthResponse>() {}.type, AuthDeserializer()).create()
                ))
                .build()
            accountClient!!
        }
    }

    fun getEventsClient(): Retrofit{
        return eventClient ?: synchronized(this){
            eventClient = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            eventClient!!
        }
    }
    fun getProfileClient(): Retrofit{
        return profileClient ?: synchronized(this){
            profileClient = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            profileClient!!
        }
    }
    fun getSkillsClient(): Retrofit{
        return skillsClient ?: synchronized(this){
            skillsClient = Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            skillsClient!!
        }
    }
}