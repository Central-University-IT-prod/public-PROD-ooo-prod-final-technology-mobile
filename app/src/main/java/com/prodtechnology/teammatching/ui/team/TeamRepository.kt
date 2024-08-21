package com.prodtechnology.teammatching.ui.team

import com.prodtechnology.teammatching.data.remote.RetrofitClient
import com.prodtechnology.teammatching.data.remote.models.IsInTeamResponse
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.data.remote.teams_members.TeamsMembersService
import retrofit2.Call

class TeamRepository {
    private val client = RetrofitClient.getTeamsMembersClient()
    private val service = client.create(TeamsMembersService::class.java)

    fun getTeamById(token: String, id: Int) : Call<TeamInfo>{
        return service.getTeamById(token, id)
    }

    fun getIsInTeam(token: String, eventId: Int) : Call<IsInTeamResponse>{
        return service.isInTeam(token, eventId)
    }

    fun sendRequest(token: String, teamId: Int) : Call<String>{
        return service.sendRequest(token, teamId)
    }
}