package com.languages4u.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.languages4u.data.NaviEvent
import com.languages4u.data.ToastEvent
import com.languages4u.tools.SingleLiveEvent

class UserProfileViewModel : ViewModel() {

    private val TAG = "UserProfileViewModel"

    private val user = FirebaseAuth.getInstance().currentUser!!

    val navigatePage: MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }

    val toastMsg: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val nickname: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onEditClicked() {
        navigatePage.value = NaviEvent.ProfileSettings.event
    }

    init {
        Log.d(TAG, user.displayName.toString())

        nickname.value = user.displayName
        email.value = user.email
    }
}
