package com.prodtechnology.teammatching.data.remote.models

import com.google.gson.annotations.SerializedName

data class IsInTeamResponse(
    @SerializedName("team_id") val teamId: Int?
)