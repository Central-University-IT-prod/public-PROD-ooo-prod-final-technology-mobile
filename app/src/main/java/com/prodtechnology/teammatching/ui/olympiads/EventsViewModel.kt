package com.prodtechnology.teammatching.ui.olympiads

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prodtechnology.teammatching.data.remote.events.EventResponse
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.data.remote.models.UserInfo
import com.prodtechnology.teammatching.ui.main.swap.TeamsMembersRepository
import com.prodtechnology.teammatching.utils.statuses.EventsStatus
import com.prodtechnology.teammatching.utils.statuses.MembersStatus
import com.prodtechnology.teammatching.utils.statuses.TeamsStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class EventsViewModel(

) : ViewModel(){
    private val repository = EventsRepository()

    private val _eventsStatus: MutableLiveData<EventsStatus> = MutableLiveData()
    val eventsStatus: LiveData<EventsStatus> = _eventsStatus

    fun getEvents(token: String){
        repository.getEventsCall("Bearer $token").enqueue(object : Callback<List<EventResponse>> {
            override fun onResponse(
                call: Call<List<EventResponse>>,
                response: Response<List<EventResponse>>
            ) {
                try {
                    if(response.isSuccessful){
                        val data = response.body()!!
                        Log.d("ApiResponse", data.toString())
                        _eventsStatus.postValue(EventsStatus.Succeed(data))
                    }else{
                        _eventsStatus.postValue(EventsStatus.Failed(response.errorBody()!!.string()))
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _eventsStatus.postValue(EventsStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<List<EventResponse>>, t: Throwable) {
                t.printStackTrace()
                _eventsStatus.postValue(EventsStatus.Failed(""))
            }

        })
    }
}