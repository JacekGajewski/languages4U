package com.languages4u.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent

class SignInViewModel : ViewModel(), ILoginCallback{
    val TAG = "SignInViewModel"

    private val firebase by lazy {
        FirebaseOperations.instance
    }

    val navigatePage : MutableLiveData<String> by lazy {
        SingleLiveEvent<String>() //SingleLiveEvent
    }

    val email : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val password : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onLoginClick(view : View) {
        // TODO; handle NULL values (empty editview)
        firebase.login(email.value!!, password.value!!, this)
    }

    fun onLoginAnonymClick(view : View) {
        firebase.loginAnonymously(this)
    }

    fun onForgotPasswordClick(view : View) {
        navigatePage.value = NaviEvent.ForgotPass.event
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        navigatePage.value = NaviEvent.MenuPage.event
    }

    override fun onFailure() {
        Log.i(TAG, "onFailure()")
    }
}
