package com.languages4u.data

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.languages4u.R
import com.languages4u.auth.ILoginCallback

class FirebaseOperations {
    private val TAG = "FirebaseOperations"

    val firebaseAuth: FirebaseAuth by lazy {
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

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, iLoginCallback: ILoginCallback) {
        Log.i(TAG, "firebaseAuthWithGoogle:" + account.id!!)

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "signInWithCredential:success")
                    iLoginCallback.onSuccess()

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    iLoginCallback.onFailure()
                }
            }
    }

    fun recoverPassword(email: String) {
//        TODO: Implement password recovering
    }

    fun currentUser() = firebaseAuth.currentUser
    fun logout() = firebaseAuth.signOut()

    companion object {
        val instance = FirebaseOperations()
    }
}

