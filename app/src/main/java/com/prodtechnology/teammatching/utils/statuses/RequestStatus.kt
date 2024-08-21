package com.prodtechnology.teammatching.utils.statuses

open class RequestStatus {
    class Succeed: RequestStatus()
    data class Failed(val error: String): RequestStatus()
}