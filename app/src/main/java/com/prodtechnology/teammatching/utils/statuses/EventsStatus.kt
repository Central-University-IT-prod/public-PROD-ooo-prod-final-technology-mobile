package com.prodtechnology.teammatching.utils.statuses

import com.prodtechnology.teammatching.data.remote.events.EventResponse
import com.prodtechnology.teammatching.data.remote.models.TeamInfo

open class EventsStatus {
    data class Succeed(val data: List<EventResponse>) : EventsStatus()
    data class Failed(val error: String) : EventsStatus()
}