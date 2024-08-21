package com.prodtechnology.teammatching.data.remote.models

data class UserInfo(
    val email: String,
    val name: String,
    val telegram: String,
    val stack: List<Skill>,
    val age: String,
    val profession: String,
    val about: String,
)
