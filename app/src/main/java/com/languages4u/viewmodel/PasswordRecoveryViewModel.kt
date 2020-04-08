package com.languages4u.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent

class PasswordRecoveryViewModel : ViewModel(), ILoginCallback {
    val TAG = "PasswordRecoveryVM"

    private val firebase by lazy {
        FirebaseOperations.instance
    }

    val navigatePage : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val email : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onResetClicked(view : View) {
        // TODO; handle NULL values (empty editview)
        firebase.recoverPassword(email.value!!)
    }

    override fun onSuccess() {
        Log.i(TAG, "onSuccess()")
        navigatePage.value = NaviEvent.Authorization.event
    }

    override fun onFailure() {
        Log.i(TAG, "onFailure()")
    }
}
