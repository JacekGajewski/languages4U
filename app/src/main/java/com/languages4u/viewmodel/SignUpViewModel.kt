package com.languages4u.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent

class SignUpViewModel : ViewModel(), ILoginCallback {

    val TAG = "SignUpViewModel"

    private val firebase by lazy {
        FirebaseOperations.instance
    }

    val navigatePage : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val email : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val password : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val repeatPassword : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onSignUpClick(view : View) {
        Log.i(TAG, "onLoginClick()")
        // TODO; handle NULL values (empty editview)
        firebase.register(email.value!!, password.value!!, this)
    }

    fun onLoginAnnonymClick(view : View) {
        Log.i(TAG, "onLoginAnnonymClick()")
        firebase.loginAnonymously(this)
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        navigatePage.value = NaviEvent.MenuPage.event
    }

    override fun onFailure() {
        Log.i(TAG, "onFailure()")
//        TODO("Not yet implemented")
    }
}
