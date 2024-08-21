package com.prodtechnology.teammatching.utils.statuses

import com.prodtechnology.teammatching.data.remote.models.TeamInfo

open class TeamsStatus {
    data class Succeed(val data: List<TeamInfo>) : TeamsStatus()
    data class Failed(val error: String) : TeamsStatus()
}