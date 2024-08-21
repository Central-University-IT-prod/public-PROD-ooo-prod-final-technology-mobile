package com.prodtechnology.teammatching.data.remote.models

import com.google.gson.annotations.SerializedName

data class UserSkills(
    @SerializedName("email") var email: String,
    @SerializedName("fullname") var fullname: String,
    @SerializedName("telegram_username") var telegram: String,
    @SerializedName("skills") var skills: List<Skill>

)
