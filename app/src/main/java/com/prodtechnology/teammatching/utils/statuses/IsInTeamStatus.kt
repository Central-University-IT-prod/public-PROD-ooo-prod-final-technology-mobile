package com.prodtechnology.teammatching.utils.statuses

import com.prodtechnology.teammatching.data.remote.models.IsInTeamResponse

open class IsInTeamStatus {
    data class Succeed(val teamId: Int): IsInTeamStatus()
    data class Failed(val error: String): IsInTeamStatus()
}