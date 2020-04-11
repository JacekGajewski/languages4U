package com.languages4u.view

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

import com.languages4u.R
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentAuthBinding
import com.languages4u.viewmodel.AuthViewModel

class AuthView : Fragment() {
    val TAG = "AuthView"

    companion object {
        fun newInstance() = AuthView()
    }

    private val firebase by lazy {
        FirebaseOperations.instance
    }

    private lateinit var viewModel: AuthViewModel
    private lateinit var binding: FragmentAuthBinding
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.authViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigatePage.observe(viewLifecycleOwner, naviObserver)
        viewModel.googleClick.observe(viewLifecycleOwner, googleClickObserver)
    }


    val naviObserver = Observer<String> { newNavi ->
        Log.d(TAG, "Navi Observer called")
        when(newNavi!!) {
            NaviEvent.LogIn.event -> navController!!.navigate(R.id.action_authView_to_signInView)
            NaviEvent.SingUp.event -> navController!!.navigate(R.id.action_authView_to_signUpView)
            NaviEvent.MenuPage.event -> navController!!.navigate(R.id.action_authView_to_menuView)
            NaviEvent.ForgotPass.event -> navController!!.navigate(R.id.action_authView_to_passwordRecoveryView)
        }
    }

    val googleClickObserver = Observer<Boolean> { googleClick ->
        Log.d(TAG, "Google click Observer called")
        if (googleClick == true) {
            loginGoogle()
        }
    }

    fun loginGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 2)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult()")
        super.onActivityResult(requestCode, resultCode, data)

        val iLoginCallback = object : ILoginCallback {
            override fun onSuccess() {
                Log.i(TAG, "Google iLoginCallback onSuccess()")
                navController!!.navigate(R.id.action_authView_to_menuView)
            }
            override fun onFailure() {
                Log.i(TAG, "Google iLoginCallback onFailure()")
            }
        }

        if (requestCode == 2) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d(TAG, "getSignedInAccountFromIntent")
            try {
                val account = task.getResult(ApiException::class.java)
                //firebase.firebaseAuthWithGoogle(account!!, iLoginCallback)
            } catch (e: ApiException) {
                Log.e(TAG, "Google sign in failed", e)
                Toast.makeText(activity, "Log in fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
