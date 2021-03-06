package com.languages4u.viewmodel.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.DataOperations
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent
import com.languages4u.data.ToastEvent
import java.lang.Exception

class SignUpViewModel : ViewModel(), ILoginCallback {

    private val TAG = "SignUpViewModel"

    /*val firebase by lazy {
        FirebaseOperations.instance
    } */
    // In purpose of injecting mock easily
    var firebase : DataOperations = FirebaseOperations.instance

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

    fun onSignUpClick() {
        Log.i(TAG, "onSignUpClick()")
        if (email.value.isNullOrBlank()) {
            Log.i(TAG, "Empty email")
            toastMsg.value = ToastEvent.EmptyEmail.event
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            Log.i(TAG, "Wrong email")
            toastMsg.value = ToastEvent.EmailInvalid.event
        } else {
            if (password.value.isNullOrBlank()) {
                Log.i(TAG, "Password empty")
                toastMsg.value = ToastEvent.EmptyPassword.event
            } else if (password.value != repeatPassword.value) {
                Log.i(TAG, "Passwords do not match")
                toastMsg.value = ToastEvent.PassNotMatch.event
            } else {
                Log.i(TAG, "firebase.login")
                firebase.register(email.value!!, password.value!!, this)
            }
        }
    }

    fun onLoginAnnonymClick() {
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
            Log.i(TAG, exception.message.toString())
            toastMsg.value = exception.message.toString()
        }
    }
}
