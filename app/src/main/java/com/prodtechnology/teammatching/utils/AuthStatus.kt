package com.prodtechnology.teammatchingadmin.utils.status

open class AuthStatus {
    data class Succeed(val token: String, val email: String): AuthStatus()
    data class Failed(val error: String): AuthStatus()
}