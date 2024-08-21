package com.prodtechnology.teammatching.data.remote.account

import com.google.gson.annotations.SerializedName

data class ErrorDetail (
    @SerializedName("msg") val message: String
)