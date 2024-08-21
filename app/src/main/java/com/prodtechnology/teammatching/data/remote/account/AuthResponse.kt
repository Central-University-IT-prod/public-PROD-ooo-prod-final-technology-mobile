package com.prodtechnology.teammatching.data.remote.account

import com.google.gson.annotations.SerializedName

data class AuthResponse (
    val token: String,
    val error: String,
)