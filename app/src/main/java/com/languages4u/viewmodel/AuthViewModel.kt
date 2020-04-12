package com.languages4u.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent

class AuthViewModel : ViewModel(), ILoginCallback {
    private val TAG = "AuthViewModel"

    private val firebase by lazy {
        FirebaseOperations.instance
    }

    val navigatePage : MutableLiveData<String> by lazy {
        SingleLiveEvent<String>() //SingleLiveEvent
    }

    fun onLoginClick(view : View) {
        Log.i(TAG, "onLoginClick()")
        navigatePage.value = NaviEvent.SignIn.event
    }

    fun onSingUpClick(view : View) {
        navigatePage.value = NaviEvent.SingUp.event
    }

    fun onLoginAnonymClick(view : View) {
        firebase.loginAnonymously(this)
    }

    fun onPassRestoreClick(view : View) {
        navigatePage.value = NaviEvent.ForgotPass.event
    }

    fun onLoginFacebookClick(view : View) {
        navigatePage.value = NaviEvent.FacebookSingIn.event
    }

    fun onLoginGoogleClick(view : View) {
        navigatePage.value = NaviEvent.GoogleSingIn.event
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        navigatePage.value = NaviEvent.MenuPage.event
    }

    override fun onFailure() {
        Log.i(TAG, "onFailure()")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
