package com.prodtechnology.teammatching.data.remote.models

import com.google.gson.annotations.SerializedName

class TeamInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("members") val users: List<User>,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)