package com.prodtechnology.teammatching.ui.create_team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prodtechnology.teammatching.data.remote.models.CreateResponse
import com.prodtechnology.teammatching.data.remote.models.TeamRequest
import com.prodtechnology.teammatching.utils.statuses.CreateTeamStatus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateTeamViewModel : ViewModel() {
    private val repository = CreateTeamRepository()

    private val _status: MutableLiveData<CreateTeamStatus> = MutableLiveData()
    val status: LiveData<CreateTeamStatus> = _status

    fun addTeam(token: String, team: TeamRequest){
        repository.addTeam("Bearer $token", team).enqueue(object : Callback<CreateResponse>{
            override fun onResponse(call: Call<CreateResponse>, response: Response<CreateResponse>) {
                try {
                    if(response.isSuccessful){
                        _status.postValue(CreateTeamStatus.Succeed(response.body()!!.id))
                    }else{
                        val error = JSONObject(response.errorBody()!!.string()).getJSONArray("detail").getJSONObject(0).getString("msg")
                        _status.postValue(CreateTeamStatus.Failed(error))
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _status.postValue(CreateTeamStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<CreateResponse>, t: Throwable) {
                t.printStackTrace()
                _status.postValue(CreateTeamStatus.Failed(""))
            }
        })
    }

}