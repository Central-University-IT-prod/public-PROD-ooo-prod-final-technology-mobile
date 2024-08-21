package com.prodtechnology.teammatching.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prodtechnology.teammatching.R
import com.prodtechnology.teammatching.data.local.AppPrefs
import com.prodtechnology.teammatching.data.remote.account.AuthResponse
import com.prodtechnology.teammatching.data.remote.models.LoginUser
import com.prodtechnology.teammatching.data.remote.models.User
import com.prodtechnology.teammatchingadmin.utils.status.AuthStatus
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(context: Context) : ViewModel() {
    private val repository = LoginRepository()

    private val _status: MutableLiveData<AuthStatus> = MutableLiveData()
    val status: LiveData<AuthStatus> = _status

    private val defaultFailMessage = context.getString(R.string.error_something)

    fun loginUser(user: LoginUser){
        repository.authUser(user).enqueue(object : Callback<AuthResponse>{
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
                    _status.postValue(AuthStatus.Failed(defaultFailMessage))
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                t.printStackTrace()
                _status.postValue(AuthStatus.Failed(defaultFailMessage))
            }

        })
    }

    fun loginUserToken(token: String){
        repository.authUserToken("Bearer $token").enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                try {
                    when (response.code()) {
                        200 -> {
                            _status.postValue(AuthStatus.Succeed(AppPrefs.getToken()!!, response.body()!!.email))
                        }
                        else -> {
                            _status.postValue(AuthStatus.Failed(defaultFailMessage) )
                        }
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                    _status.postValue(AuthStatus.Failed(defaultFailMessage))
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                _status.postValue(AuthStatus.Failed(defaultFailMessage))
            }

        })
    }
}