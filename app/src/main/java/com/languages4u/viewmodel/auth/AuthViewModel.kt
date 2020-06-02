package com.languages4u.viewmodel.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.DataOperations
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent
import java.lang.Exception

class AuthViewModel : ViewModel(), ILoginCallback {
    private val TAG = "AuthViewModel"

    var firebase : DataOperations = FirebaseOperations.instance

    val navigatePage : MutableLiveData<String> by lazy {
        SingleLiveEvent<String>() //SingleLiveEvent
    }

    val toastMsg : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onLoginClick() {
        Log.i(TAG, "onLoginClick()")
        navigatePage.value = NaviEvent.SignIn.event
    }

    fun onSingUpClick() {
        navigatePage.value = NaviEvent.SingUp.event
    }

    fun onLoginAnonymClick() {
        firebase.loginAnonymously(this)
    }

    fun onPassRestoreClick() {
        navigatePage.value = NaviEvent.ForgotPass.event
    }

    fun onLoginFacebookClick() {
        navigatePage.value = NaviEvent.FacebookSingIn.event
    }

    fun onLoginGoogleClick() {
        navigatePage.value = NaviEvent.GoogleSingIn.event
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        navigatePage.value = NaviEvent.MenuPage.event
    }

    override fun onFailure(exception: Exception?) {
        Log.i(TAG, "onFailure()")
        if (exception != null){
            Log.i(TAG, exception.message.toString())
            toastMsg.value = exception.message.toString()
        }
    }
}
