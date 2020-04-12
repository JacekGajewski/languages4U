package com.languages4u.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent

class PasswordRecoveryViewModel : ViewModel(), ILoginCallback {
    val TAG = "PasswordRecoveryVM"

    private val firebase by lazy {
        FirebaseOperations.instance
    }

    val navigatePage : MutableLiveData<String> by lazy {
        SingleLiveEvent<String>() //SingleLiveEvent
    }
    val email : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onResetClicked() {
        if (email.value.isNullOrBlank()
            || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            navigatePage.value = NaviEvent.EmptyInput.event
        } else {
            firebase.recoverPassword(email.value!!, this)
        }
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        navigatePage.value = NaviEvent.Authorization.event
    }

    override fun onFailure() {
        Log.i(TAG, "onFailure()")
        navigatePage.value = NaviEvent.ForgotPassError.event
    }
}
