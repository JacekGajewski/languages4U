package com.languages4u.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.languages4u.R


class AuthActivity : AppCompatActivity(), IAuth {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

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

    override fun signUp() {
        TODO("Not yet implemented")
    }

    override fun login() {
        TODO("Not yet implemented")
    }

    override fun loginAnonymously() {
        TODO("Not yet implemented")
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
}

