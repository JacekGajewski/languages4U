package com.languages4u.viewmodel.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.DataOperations
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.data.ToastEvent
import com.languages4u.tools.SingleLiveEvent
import java.lang.Exception

class SignInViewModel : ViewModel(), ILoginCallback {
    val TAG = "SignInViewModel"

    var firebase : DataOperations = FirebaseOperations.instance

    val navigatePage: MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val password: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val toastMsg: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    fun onLoginClick() {
        if (email.value.isNullOrBlank()) {
            Log.i(TAG, "Empty email")
            toastMsg.value = ToastEvent.EmptyEmail.event
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            Log.i(TAG, "Wrong email")
            toastMsg.value = ToastEvent.EmailInvalid.event
        } else {
            if (password.value.isNullOrBlank()) {
                Log.i(TAG, "Empty email")
                toastMsg.value = ToastEvent.EmptyPassword.event
            } else {
                firebase.login(email.value!!, password.value!!, this)
            }
        }
    }

    fun onLoginAnonymClick() {
        firebase.loginAnonymously(this)
    }

    fun onForgotPasswordClick() {
        navigatePage.value = NaviEvent.ForgotPass.event
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
