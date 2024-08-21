package com.prodtechnology.teammatching.ui.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.reflect.TypeToken
import com.prodtechnology.teammatching.data.remote.models.IsInTeamResponse
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.utils.statuses.IsInTeamStatus
import com.prodtechnology.teammatching.utils.statuses.RequestStatus
import com.prodtechnology.teammatching.utils.statuses.TeamStatus
import com.prodtechnology.teammatching.utils.statuses.TeamsStatus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamViewModel : ViewModel() {
    private val repository = TeamRepository()

    private val _teamStatus: MutableLiveData<TeamStatus> = MutableLiveData()
    val teamStatus: LiveData<TeamStatus> = _teamStatus

    private val _isInTeam: MutableLiveData<IsInTeamStatus> = MutableLiveData()
    val isInTeam: LiveData<IsInTeamStatus> = _isInTeam

    private val _requestStatus: MutableLiveData<RequestStatus> = MutableLiveData()
    val requestStatus: LiveData<RequestStatus> = _requestStatus

    fun sendRequest(token: String, teamId: Int){
        repository.sendRequest("Bearer $token", teamId).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                try {
                    if(response.isSuccessful){
                        _requestStatus.postValue(RequestStatus.Succeed())
                    }else{
                        val error = JSONObject(response.errorBody()!!.string()).getJSONArray("detail").getJSONObject(0).getString("msg")
                        _requestStatus.postValue(RequestStatus.Failed(error))
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _requestStatus.postValue(RequestStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                _requestStatus.postValue(RequestStatus.Failed(""))
            }

        })
    }
    fun getIsInTeam(token: String, eventId: Int){
        repository.getIsInTeam("Bearer $token", eventId).enqueue(object : Callback<IsInTeamResponse>{
            override fun onResponse(
                call: Call<IsInTeamResponse>,
                response: Response<IsInTeamResponse>
            ) {
                try {
                    if(response.isSuccessful){
                        if(response.body()!!.teamId != null){
                            _isInTeam.postValue(IsInTeamStatus.Succeed(response.body()!!.teamId!!))
                        }else{
                            _isInTeam.postValue(IsInTeamStatus.Failed(""))
                        }
                    }else{
                        _isInTeam.postValue(IsInTeamStatus.Failed(""))
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _isInTeam.postValue(IsInTeamStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<IsInTeamResponse>, t: Throwable) {
                t.printStackTrace()
                _isInTeam.postValue(IsInTeamStatus.Failed(""))
            }

        })
    }
    fun getTeamById(token: String, id: Int){
        repository.getTeamById("Bearer $token", id).enqueue(object : Callback<TeamInfo>{
            override fun onResponse(call: Call<TeamInfo>, response: Response<TeamInfo>) {
                try {
                    if(response.isSuccessful){
                        _teamStatus.postValue(TeamStatus.Succeed(response.body()!!))
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _teamStatus.postValue(TeamStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<TeamInfo>, t: Throwable) {
                t.printStackTrace()
                _teamStatus.postValue(TeamStatus.Failed(""))
            }
        })
    }
}