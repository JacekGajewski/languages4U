package com.languages4u.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.languages4u.R
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment() {

    private lateinit var callback: IAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callback = context as IAuth
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        card_signup_button.setOnClickListener {
            callback.signUp(edit_signup_email.text.toString(), edit_signup_password.text.toString())
        }

        text_signup_anonymously.setOnClickListener {
            callback.loginAnonymously()
        }

        super.onActivityCreated(savedInstanceState)
    }
}

