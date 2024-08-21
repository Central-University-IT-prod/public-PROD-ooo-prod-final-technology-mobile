package com.prodtechnology.teammatching.utils.statuses

open class CreateTeamStatus {
    data class Succeed(val id: Int): CreateTeamStatus()
    data class Failed(val error: String): CreateTeamStatus()
}