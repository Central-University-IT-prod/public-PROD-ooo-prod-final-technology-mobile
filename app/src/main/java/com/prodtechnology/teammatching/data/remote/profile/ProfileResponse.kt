package com.prodtechnology.teammatching.data.remote.profile

import com.google.gson.annotations.SerializedName
import com.prodtechnology.teammatching.data.remote.models.Skill

class ProfileResponse (
    @SerializedName("email") val emailProfile: String,
    @SerializedName("fullname") val fullName: String,
    @SerializedName("telegram_username") val telegram: String,
    @SerializedName("skills") val stake: List<Skill>,
    @SerializedName("bio") val about: String,
    @SerializedName("profession") val profession: String,
    @SerializedName("age") val age: Int
)