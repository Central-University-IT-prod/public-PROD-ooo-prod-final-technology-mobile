package com.prodtechnology.teammatching.ui.main.swap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.models.UserInfo
import com.prodtechnology.teammatching.utils.statuses.MembersStatus
import com.prodtechnology.teammatching.utils.statuses.TeamsStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class TeamsMembersViewModel(

) : ViewModel(){
    private val repository = TeamsMembersRepository()

    private val _teamsStatus: MutableLiveData<TeamsStatus> = MutableLiveData()
    val teamsStatus: LiveData<TeamsStatus> = _teamsStatus

    private val _membersStatus: MutableLiveData<MembersStatus> = MutableLiveData()
    val membersStatus: LiveData<MembersStatus> = _membersStatus

    fun getTeams(token: String, eventId: Int){
        repository.getTeamsCall("Bearer $token", eventId).enqueue(object : Callback<List<TeamInfo>> {
            override fun onResponse(
                call: Call<List<TeamInfo>>,
                response: Response<List<TeamInfo>>
            ) {
                try {
                    if(response.code() == 200){
                        val data = response.body()!!
                        _teamsStatus.postValue(TeamsStatus.Succeed(data))
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _teamsStatus.postValue(TeamsStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<List<TeamInfo>>, t: Throwable) {
                t.printStackTrace()
                _teamsStatus.postValue(TeamsStatus.Failed(""))
            }


        })
    }

    fun getMembers(token: String, eventId: Int){
        repository.getMembersCall("Bearer $token", eventId).enqueue(object : Callback<List<User>> {
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                try {
                    if(response.code() == 200){
                        val data = response.body()!!
                        _membersStatus.postValue(MembersStatus.Succeed(data))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _membersStatus.postValue(MembersStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
                _membersStatus.postValue(MembersStatus.Failed(""))
            }
        })
    }
}