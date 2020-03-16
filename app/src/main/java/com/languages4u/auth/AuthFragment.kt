package com.languages4u.auth

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.getApplicationContext

import com.languages4u.R
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : Fragment() {

    private lateinit var callback: IAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as IAuth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_auth, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        card_authorization_login.setOnClickListener {
            callback.loginClicked()
        }

        card_authorization_signup.setOnClickListener {
            callback.signUpClicked()
        }

        text_main_forgotpassword.setOnClickListener {
            callback.passwordForgottenClicked()
        }

        text_authorization_anonymously.setOnClickListener {
            callback.loginAnonymously()
        }

        card_authorization_google.setOnClickListener {
            callback.loginGoogle()
        }

        login_button_facebook.setOnClickListener {
            callback.loginFacebook()
        }

        super.onActivityCreated(savedInstanceState)
    }
}
