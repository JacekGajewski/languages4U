package com.languages4u.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.languages4u.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var callback: IAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        callback = context as IAuth
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        card_login_button.setOnClickListener {
            callback.login(edit_login_email.text.toString(), edit_login_password.text.toString())
        }

        super.onActivityCreated(savedInstanceState)
    }

}
