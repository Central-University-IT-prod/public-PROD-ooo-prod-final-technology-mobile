package com.prodtechnology.teammatching.utils.statuses

import com.prodtechnology.teammatching.data.remote.events.EventResponse
import com.prodtechnology.teammatching.data.remote.profile.ProfileResponse

open class ProfileStatuse {
    data class Succeed(val data: ProfileResponse) : ProfileStatuse()
    data class Failed(val error: String) : ProfileStatuse()
}