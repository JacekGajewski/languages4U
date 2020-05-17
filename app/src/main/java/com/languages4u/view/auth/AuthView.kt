package com.languages4u.view.auth

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
import com.languages4u.data.ToastEvent
import com.languages4u.databinding.FragmentAuthBinding
import com.languages4u.viewmodel.auth.AuthViewModel
import kotlinx.android.synthetic.main.fragment_auth.*
import java.lang.Exception

class AuthView : Fragment() {
    val TAG = "AuthView"

    private val RC_SIGN_IN = 102 //GOOGLE REQUEST CODE

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

        if (auth.currentUser != null) {
            navController!!.navigate(R.id.action_authView_to_menuView)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigatePage.observe(viewLifecycleOwner, naviObserver)
    }


    private val naviObserver = Observer<String> { newNavi ->
        Log.d(TAG, "Navi Observer called")
        when (newNavi!!) {
            NaviEvent.SignIn.event -> navController!!.navigate(R.id.action_authView_to_signInView)
            NaviEvent.SingUp.event -> navController!!.navigate(R.id.action_authView_to_signUpView)
            NaviEvent.GoogleSingIn.event -> loginGoogle()
            NaviEvent.FacebookSingIn.event -> loginFacebook()
            NaviEvent.MenuPage.event -> navController!!.navigate(R.id.action_authView_to_menuView)
            NaviEvent.ForgotPass.event -> navController!!.navigate(R.id.action_authView_to_passwordRecoveryView)
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
                showToast(ToastEvent.SignInCancelled.event)
            }

            override fun onError(exception: FacebookException) {
                Log.d(TAG, "facebook:onError", exception)
                showToast(exception.message)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithCredential:success")
                navController!!.navigate(R.id.action_authView_to_menuView)
            } else {
                Log.d(TAG, "signInWithCredential:error")
                showToast(ToastEvent.FacebookSignInError.event)
            }
        }
    }

    fun loginGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(TAG, "onActivityResult()")
        super.onActivityResult(requestCode, resultCode, data)

        // GOOGLE
        if (requestCode == RC_SIGN_IN) {

            val iLoginCallback = object : ILoginCallback {
                override fun onSuccess() {
                    Log.i(TAG, "Google iLoginCallback onSuccess()")
                    // is success then navigate to menu view
                    // functionality extracted from viewModel, because of onActivityResult dependency
                    // (workaround)
                    navController!!.navigate(R.id.action_authView_to_menuView)
                }

                override fun onFailure(exception: Exception?) {
                    Log.e(TAG, "Google iLoginCallback onFailure()")
                    if (exception != null) {
                        showToast(exception.message)
                    }
                }
            }

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            Log.d(TAG, "getSignedInAccountFromIntent")
            try {
                val account = task.getResult(ApiException::class.java)
                firebase.firebaseAuthWithGoogle(account!!, iLoginCallback)
            } catch (e: ApiException) {
                Log.e(TAG, "Google sign in failed", e)
                showToast(ToastEvent.SignInFail.event)
            }
        } else { // FACEBOOK
            fbCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}
