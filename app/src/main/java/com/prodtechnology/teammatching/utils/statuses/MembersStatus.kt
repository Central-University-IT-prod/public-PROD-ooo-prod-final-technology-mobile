package com.prodtechnology.teammatching.utils.statuses

import com.prodtechnology.teammatching.data.remote.models.User

open class MembersStatus {
    data class Succeed(val data: List<User>) : MembersStatus()
    data class Failed(val error: String) : MembersStatus()
}