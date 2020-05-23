package com.languages4u.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.languages4u.auth.ILoginCallback

interface DataOperations {
    fun login(email: String, password: String, iLoginCallback: ILoginCallback)
    fun register(email: String, password: String, iLoginCallback: ILoginCallback)
    fun loginAnonymously(iLoginCallback: ILoginCallback)
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount, iLoginCallback: ILoginCallback)
    fun recoverPassword(email: String, callback: ILoginCallback)
    fun currentUser() : FirebaseUser?
    fun logout()

    fun registerAuthStateListener(listener: FirebaseAuth.AuthStateListener)
    fun registerListener(listener : OnUserStateChangeListener)
}