package com.prodtechnology.teammatching.ui.profil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prodtechnology.teammatching.data.remote.profile.ProfileResponse
import com.prodtechnology.teammatching.utils.statuses.ProfileStatuse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ProfileViewModel(

) : ViewModel(){
    private val repository = ProfileRepository()

    private val _profileStatus: MutableLiveData<ProfileStatuse> = MutableLiveData()
    val profileStatus: LiveData<ProfileStatuse> = _profileStatus


    fun getProfile(token: String){
        repository.getProfileCall("Bearer $token").enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                try {
                    if(response.code() == 200){
                        val data = response.body()!!
                        _profileStatus.postValue(ProfileStatuse.Succeed(data))
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _profileStatus.postValue(ProfileStatuse.Failed(""))
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                t.printStackTrace()
                _profileStatus.postValue(ProfileStatuse.Failed(""))
            }


        })
    }
}