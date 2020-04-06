package com.languages4u.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.languages4u.auth.ILoginCallback

class FirebaseOperations {
    private val TAG = "FirebaseOperations"

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String, iLoginCallback: ILoginCallback) {
        Log.i(TAG, "login()")
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    iLoginCallback.onSuccess()
                } else {
                    iLoginCallback.onFailure()
                }
        }
    }


    fun register(email: String, password: String, iLoginCallback: ILoginCallback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                iLoginCallback.onSuccess()
            } else {
                iLoginCallback.onFailure()
            }
        }
    }

    fun loginAnonymously(iLoginCallback: ILoginCallback) {
        firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                iLoginCallback.onSuccess()
            } else {
                iLoginCallback.onFailure()
            }
        }
    }

    fun currentUser() = firebaseAuth.currentUser
    fun logout() = firebaseAuth.signOut()

    companion object {
        val instance = FirebaseOperations()
    }
}

