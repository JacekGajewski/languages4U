package com.languages4u.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.data.ToastEvent
import com.languages4u.tools.SingleLiveEvent

class PasswordRecoveryViewModel : ViewModel(), ILoginCallback {
    val TAG = "PasswordRecoveryVM"

    private val firebase by lazy {
        FirebaseOperations.instance
    }

    val navigatePage: MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }
    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val toastMsg: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onResetClicked() {
        if (email.value.isNullOrBlank()) {
            Log.i(TAG, "Empty email")
            toastMsg.value = ToastEvent.EmptyEmail.event
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) {
            Log.i(TAG, "Wrong email")
            toastMsg.value = ToastEvent.EmailInvalid.event
        } else {
            firebase.recoverPassword(email.value!!, this)
        }
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        toastMsg.value = ToastEvent.ResetPasswordSuccess.event
        navigatePage.value = NaviEvent.Authorization.event
    }

    override fun onFailure() {
        Log.i(TAG, "onFailure()")
        toastMsg.value = ToastEvent.WrongEmail.event
    }
}
