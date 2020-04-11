package com.languages4u.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.languages4u.R
import com.languages4u.auth.ILoginCallback
import com.languages4u.data.FirebaseOperations
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentAuthBinding
import com.languages4u.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthView : Fragment() {
    val TAG = "AuthView"

    private lateinit var fbCallbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth

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

        auth = FirebaseAuth.getInstance()

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
        viewModel.facebookClick.observe(viewLifecycleOwner, facebookClickObserver)
    }


    private val naviObserver = Observer<String> { newNavi ->
        Log.d(TAG, "Navi Observer called")
        when (newNavi!!) {
            NaviEvent.LogIn.event -> navController!!.navigate(R.id.action_authView_to_signInView)
            NaviEvent.SingUp.event -> navController!!.navigate(R.id.action_authView_to_signUpView)
            NaviEvent.MenuPage.event -> navController!!.navigate(R.id.action_authView_to_menuView)
            NaviEvent.ForgotPass.event -> navController!!.navigate(R.id.action_authView_to_passwordRecoveryView)
        }
    }

    private val googleClickObserver = Observer<Boolean> { googleClick ->
        Log.d(TAG, "Google click Observer called")
        if (googleClick == true) {
            loginGoogle()
        }
    }

    private val facebookClickObserver = Observer<Boolean> { facebookClick ->
        Log.d(TAG, "Facebook click Observer called")
        if (facebookClick == true) {
            loginFacebook()
        }
    }

    private fun loginFacebook() {
        //  TODO: Disable Facebook Analytics
        fbCallbackManager = CallbackManager.Factory.create()

        login_button_facebook.setPermissions(listOf("email"))
        login_button_facebook.fragment = this
        login_button_facebook.registerCallback(fbCallbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser
                navController!!.navigate(R.id.action_authView_to_menuView)
            } else {
                Log.d(TAG, "signInWithCredential:error")
            }
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

        fbCallbackManager.onActivityResult(requestCode, resultCode, data)

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
                firebase.firebaseAuthWithGoogle(account!!, iLoginCallback)
            } catch (e: ApiException) {
                Log.e(TAG, "Google sign in failed", e)
                Toast.makeText(activity, "Log in fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
