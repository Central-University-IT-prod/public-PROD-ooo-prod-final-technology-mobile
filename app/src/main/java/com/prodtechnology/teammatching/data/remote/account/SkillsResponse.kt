package com.prodtechnology.teammatching.data.remote.account

import com.google.gson.annotations.SerializedName

data class SkillsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)
