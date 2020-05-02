package com.languages4u.data

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.languages4u.auth.ILoginCallback

open class FirebaseOperations : DataOperations {
    private val TAG = "FirebaseOperations"

    val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun login(email: String, password: String, iLoginCallback: ILoginCallback) {
        Log.i(TAG, "login()")
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                iLoginCallback.onSuccess()
            } else {
                iLoginCallback.onFailure(task.exception)
            }
        }

    }

    override fun register(email: String, password: String, iLoginCallback: ILoginCallback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                iLoginCallback.onSuccess()
            } else {
                iLoginCallback.onFailure(task.exception)
            }
        }
    }

    override fun loginAnonymously(iLoginCallback: ILoginCallback) {
        firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                iLoginCallback.onSuccess()
            } else {
                iLoginCallback.onFailure(task.exception)
            }
        }
    }

    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount, iLoginCallback: ILoginCallback) {
        Log.i(TAG, "firebaseAuthWithGoogle:" + account.id!!)

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.i(TAG, "signInWithCredential:success")
                    iLoginCallback.onSuccess()

                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    iLoginCallback.onFailure(task.exception)
                }
            }
    }

    override fun recoverPassword(email: String, callback: ILoginCallback) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                callback.onSuccess()
            }
            else {
                callback.onFailure(task.exception)
                callback.onFailure(null)
            }
        }
    }

    override fun currentUser() = firebaseAuth.currentUser
    override fun logout() = firebaseAuth.signOut()

    companion object {
        val instance = FirebaseOperations()
    }
}

