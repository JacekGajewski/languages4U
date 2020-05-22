package com.languages4u.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.languages4u.data.NaviEvent
import com.languages4u.data.ToastEvent
import com.languages4u.tools.SingleLiveEvent

class UserSettingsViewModel : ViewModel() {

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

    val newNickname: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val oldPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val newPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }


    fun onUpdateNicknameClicked() {

        Log.d(TAG, "onUpdateNicknameClicked()")

        if (oldPassword.value.isNullOrBlank()) {
            toastMsg.value = ToastEvent.EmptyPassword.event
            return
        } else if (newNickname.value.isNullOrBlank()) {
            toastMsg.value = ToastEvent.NicknameEmpty.event
            return
        }

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(newNickname.value)
            .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    toastMsg.value = ToastEvent.SuccessfulUpdate.event
                    navigatePage.value = NaviEvent.ProfileView.event
                } else {
                    toastMsg.value = ToastEvent.SuccessfulUpdate.event
                }
            }
    }

    fun onUpdateEmailClicked() {

        Log.d(TAG, "onUpdateEmailClicked()")

        if (oldPassword.value.isNullOrBlank()) {
            toastMsg.value = ToastEvent.EmptyPassword.event
            return
        } else if (email.value.isNullOrBlank()) {
            toastMsg.value = ToastEvent.EmptyEmail.event
            return
        }

        val credential = EmailAuthProvider
            .getCredential(user.email!!, oldPassword.value!!)

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
            .addOnCompleteListener { authTask ->
                Log.d(TAG, "User re-auth")
                if (authTask.isSuccessful) {
                    user.updateEmail(email.value!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User email address updated.")
                                toastMsg.value = ToastEvent.SuccessfulUpdate.event
                                navigatePage.value = NaviEvent.ProfileView.event
                            } else {
                                Log.d(TAG, task.exception!!.message.toString())
                                toastMsg.value = ToastEvent.WrongCredentials.event
                            }
                        }
                } else {
                    Log.d(TAG, authTask.exception!!.message.toString())
                    toastMsg.value = ToastEvent.WrongCredentials.event
                }
            }
    }

    fun onUpdatePasswordClicked() {

        Log.d(TAG, "onUpdatePasswordClicked()")

        if (oldPassword.value.isNullOrBlank()) {
            toastMsg.value = ToastEvent.EmptyPassword.event
            return
        } else if (newPassword.value.isNullOrBlank()) {
            toastMsg.value = ToastEvent.EmptyPassword.event
            return
        }

        val credential = EmailAuthProvider
            .getCredential(user.email!!, oldPassword.value!!)

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
            .addOnCompleteListener { authTask ->
                Log.d(TAG, "User re-auth")
                if (authTask.isSuccessful) {
                    user.updatePassword(newPassword.value!!)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "User email address updated.")
                                toastMsg.value = ToastEvent.SuccessfulUpdate.event
                                navigatePage.value = NaviEvent.ProfileView.event
                            } else {
                                Log.d(TAG, task.exception!!.message.toString())
                                toastMsg.value = ToastEvent.WrongCredentials.event
                            }
                        }
                } else {
                    Log.d(TAG, authTask.exception!!.message.toString())
                    toastMsg.value = ToastEvent.WrongCredentials.event
                }
            }

    }

    fun onDeleteClicked() {
        Log.d(TAG, "onDeleteClicked()")

        if (oldPassword.value.isNullOrBlank()) {
            toastMsg.value = ToastEvent.EmptyPassword.event
            return
        }

        val credential = EmailAuthProvider
            .getCredential(user.email!!, oldPassword.value!!)

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
            .addOnCompleteListener { authTask ->
                Log.d(TAG, "User re-auth")
                if (authTask.isSuccessful) {
                    user.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User account deleted.")
                            toastMsg.value = ToastEvent.AccountDeleted.event
                        } else {
                            Log.d(TAG, task.exception!!.message.toString())
                            toastMsg.value = ToastEvent.ErrorAccountDeleted.event
                        }
                    }
                } else {
                    Log.d(TAG, authTask.exception!!.message.toString())
                    toastMsg.value = ToastEvent.WrongCredentials.event
                }
            }
    }
}
