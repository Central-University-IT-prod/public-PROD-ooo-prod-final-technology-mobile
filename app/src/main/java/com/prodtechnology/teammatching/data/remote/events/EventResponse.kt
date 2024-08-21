package com.prodtechnology.teammatching.data.remote.events

import com.google.gson.annotations.SerializedName

data class EventResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("max_member_qty") val maxMembers: String,
    @SerializedName("team_creation_deadline") val deadline: String
)
