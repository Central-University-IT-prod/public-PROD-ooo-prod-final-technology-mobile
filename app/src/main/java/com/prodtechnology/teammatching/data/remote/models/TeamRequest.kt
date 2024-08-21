package com.prodtechnology.teammatching.data.remote.models

import com.google.gson.annotations.SerializedName

data class TeamRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("event_id") val eventId: Int
)
