package com.languages4u.auth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.languages4u.MainActivity
import com.languages4u.R


class AuthActivity : AppCompatActivity(), IAuth {

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private val TAG = "AuthActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()

        startFragment(AuthFragment())
    }

    override fun loginClicked() {
        startFragment(LoginFragment())
    }

    override fun signUpClicked() {
        startFragment(SignUpFragment())
    }

    override fun passwordForgottenClicked() {
        startFragment(PasswordRecoveryFragment())
    }

    override fun signUp(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(TAG, "createUserWithEmail:success")
                if (task.isSuccessful) {
                    startMainActivity(auth.currentUser)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    startMainActivity(auth.currentUser);
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun loginAnonymously() {
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInAnonymously:success")
                    startMainActivity(auth.currentUser);
                } else {
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun loginGoogle() {
        TODO("Not yet implemented")
    }

    override fun loginFacebook() {
        TODO("Not yet implemented")
    }

    private fun startFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_main, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun startMainActivity(user: FirebaseUser?) {
//        TODO: Pass the user data to MainActivity.
        val intent = Intent(this, MainActivity::class.java).apply { }
        startActivity(intent)
    }
}

