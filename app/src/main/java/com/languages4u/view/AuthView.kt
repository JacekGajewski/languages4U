package com.languages4u.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentAuthBinding
import com.languages4u.viewmodel.AuthViewModel

class AuthView : Fragment() {
    val TAG = "AuthView"

    companion object {
        fun newInstance() = AuthView()
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
    }


    val naviObserver = Observer<String> { newNavi ->
        Log.d(TAG, "Observer called")
        when(newNavi!!) {
            NaviEvent.LogIn.event -> navController!!.navigate(R.id.action_authView_to_signInView)
            NaviEvent.SingUp.event -> navController!!.navigate(R.id.action_authView_to_signUpView)
            NaviEvent.MenuPage.event -> navController!!.navigate(R.id.action_authView_to_menuView)
            NaviEvent.ForgotPass.event -> navController!!.navigate(R.id.action_authView_to_passwordRecoveryView)
        }
    }





}
