package com.prodtechnology.teammatching.data.remote.models

import com.google.gson.annotations.SerializedName

data class LoginUser (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String

)