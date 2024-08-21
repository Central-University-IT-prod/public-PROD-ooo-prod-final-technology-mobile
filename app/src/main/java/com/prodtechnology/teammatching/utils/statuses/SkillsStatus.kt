package com.prodtechnology.teammatching.utils.statuses

import com.prodtechnology.teammatching.data.remote.account.SkillsResponse

open class SkillsStatus {
    data class Succeed(val data: List<SkillsResponse>) : SkillsStatus()
    data class Failed(val error: String) : SkillsStatus()
}