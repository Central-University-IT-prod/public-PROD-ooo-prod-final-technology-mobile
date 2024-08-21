package com.prodtechnology.teammatching.data.remote.teams_members

import com.prodtechnology.teammatching.data.remote.models.CreateResponse
import com.prodtechnology.teammatching.data.remote.models.IsInTeamResponse
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.data.remote.models.TeamRequest
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.models.UserInfo
import com.prodtechnology.teammatching.utils.statuses.TeamStatus
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamsMembersService {
    @GET("api/events/free-teams")
    fun getTeams(
        @Header("Authorization") token: String,
        @Query("event_id") eventId: Int
    ) : Call<List<TeamInfo>>

    @GET("api/events/free-users")
    fun getMembers(
        @Header("Authorization") token: String,
        @Query("event_id") eventId: Int
    ) : Call<List<User>>

    @GET("api/teams/{team_id}")
    fun getTeamById(
        @Header("Authorization") token: String,
        @Path("team_id") teamId: Int
    ) : Call<TeamInfo>

    @POST("api/teams")
    fun addTeam(
        @Header("Authorization") token: String,
        @Body team: TeamRequest
    ) : Call<CreateResponse>

    @GET("api/users/is_in_team")
    fun isInTeam(
        @Header("Authorization") token: String,
        @Query("event_id") eventId: Int
    ) : Call<IsInTeamResponse>

    @POST("api/teams/req/{team_id}")
    fun sendRequest(
        @Header("Authorization") token: String,
        @Path("team_id") teamId: Int
    ) : Call<String>
}