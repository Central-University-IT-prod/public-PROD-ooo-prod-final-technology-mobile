package com.prodtechnology.teammatching.utils.statuses

import com.prodtechnology.teammatching.data.remote.models.TeamInfo

open class TeamStatus {
    data class Succeed(val team: TeamInfo) : TeamStatus()
    data class Failed(val error: String) : TeamStatus()
}