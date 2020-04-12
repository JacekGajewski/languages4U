package com.languages4u.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent
import com.languages4u.data.ToastEvent
import java.lang.Exception

class SignUpViewModel : ViewModel(), ILoginCallback {

    val TAG = "SignUpViewModel"

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

    val repeatPassword : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val toastMsg : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onSignUpClick(view : View) {
        Log.i(TAG, "onSignUpClick()")
        if (password.value == null) {
            Log.i(TAG, "Password = null")
            toastMsg.value = ToastEvent.WrongPassword.event
        } else if (password.value != repeatPassword.value) {
            Log.i(TAG, "Passwords do not match")
            toastMsg.value = ToastEvent.PassNotMatch.event
        } else {
            Log.i(TAG, "firebase.login")
            firebase.register(email.value!!, password.value!!, this)
        }
    }

    fun onLoginAnnonymClick(view : View) {
        Log.i(TAG, "onLoginAnnonymClick()")
        firebase.loginAnonymously(this)
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        navigatePage.value = NaviEvent.MenuPage.event
    }

    override fun onFailure(exception: Exception?) {
        Log.i(TAG, "onFailure()")
        if (exception != null){
            toastMsg.value = exception.message
        }
    }
}
