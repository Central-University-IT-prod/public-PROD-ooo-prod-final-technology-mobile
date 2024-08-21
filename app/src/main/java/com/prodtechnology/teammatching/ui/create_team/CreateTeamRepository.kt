package com.prodtechnology.teammatching.ui.create_team

import android.icu.text.CaseMap.Title
import com.google.gson.reflect.TypeToken
import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.models.CreateResponse
import com.prodtechnology.teammatching.data.remote.models.TeamRequest
import com.prodtechnology.teammatching.data.remote.teams_members.TeamsMembersService
import retrofit2.Call

class CreateTeamRepository {
    private val client = RetrofitClient.getTeamsMembersClient()
    private val service = client.create(TeamsMembersService::class.java)

    fun addTeam(token: String, team: TeamRequest): Call<CreateResponse>{
        return service.addTeam(token, team)
    }
}