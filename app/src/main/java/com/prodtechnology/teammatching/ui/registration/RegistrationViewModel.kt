package com.prodtechnology.teammatching.ui.registration

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.data.remote.account.AuthResponse
import com.prodtechnology.teammatching.data.remote.account.SkillsResponse
import com.prodtechnology.teammatching.data.remote.models.TeamInfo
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatching.data.remote.models.UserRequest
import com.prodtechnology.teammatching.utils.statuses.SkillsStatus
import com.prodtechnology.teammatching.utils.statuses.TeamsStatus
import com.prodtechnology.teammatchingadmin.utils.status.AuthStatus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel(context: Context) : ViewModel() {
    private val repository = RegistrationRepository()

    private val _status: MutableLiveData<AuthStatus> = MutableLiveData()
    val status: LiveData<AuthStatus> = _status

    private val _status_skills: MutableLiveData<SkillsStatus> = MutableLiveData()
    val status_skills: LiveData<SkillsStatus> = _status_skills

    private val defaultErrorMessage = context.getString(R.string.error_something)

    fun signUpUser(user: UserRequest){
        repository.signUpUser(user).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                try {
                    when (response.code()) {
                        200 -> {
                            _status.postValue(AuthStatus.Succeed(response.body()!!.token, user.email))
                        }
                        else -> {
                            val error = JSONObject(response.errorBody()!!.string()).getJSONArray("detail").getJSONObject(0).getString("msg")
                            _status.postValue(AuthStatus.Failed(error))
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _status.postValue(AuthStatus.Failed(defaultErrorMessage))
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                t.printStackTrace()
                _status.postValue(AuthStatus.Failed(defaultErrorMessage))
            }
        })
    }

    fun getSkills(){
        repository.getSkills().enqueue(object : Callback<List<SkillsResponse>> {
            override fun onResponse(
                call: Call<List<SkillsResponse>>,
                response: Response<List<SkillsResponse>>
            ) {
                try {
                    if(response.code() == 200){
                        val data = response.body()!!
                        _status_skills.postValue(SkillsStatus.Succeed(data))
                    }
                }catch (e: java.lang.Exception){
                    e.printStackTrace()
                    _status_skills.postValue(SkillsStatus.Failed(""))
                }
            }

            override fun onFailure(call: Call<List<SkillsResponse>>, t: Throwable) {
                t.printStackTrace()
                _status_skills.postValue(SkillsStatus.Failed(""))
            }
        })
    }
}