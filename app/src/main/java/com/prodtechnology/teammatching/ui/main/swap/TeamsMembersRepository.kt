package com.prodtechnology.teammatching.ui.main.swap

import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.teams_members.TeamsMembersService
import com.prodtechnology.teammatching.data.remote.models.UserInfo
import retrofit2.Call

class TeamsMembersRepository {
    private val client = RetrofitClient.getTeamsMembersClient()
    private val service = client.create(TeamsMembersService::class.java)

    fun getTeamsCall(token: String, eventId: Int): Call<List<TeamInfo>>{
        return service.getTeams(token, eventId)
    }

    fun getMembersCall(token: String, eventId: Int) : Call<List<User>>{
        return service.getMembers(token, eventId)
    }
}