package com.languages4u.view.auth

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
import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentSignUpBinding
import com.languages4u.viewmodel.auth.SignUpViewModel

class SignUpView : Fragment() {
    private val TAG = "SignUpView"

    companion object {
        fun newInstance() = SignUpView()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding: FragmentSignUpBinding
    var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        binding.signUpViewModel = viewModel
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
        viewModel.toastMsg.observe(viewLifecycleOwner, toastMsgObserver)
    }

    val naviObserver = Observer<String> { newNavi ->
        Log.d(TAG, "Observer called")
        when (newNavi!!) {
            NaviEvent.MenuPage.event -> navController!!.navigate(R.id.action_signUpView_to_menuView)
        }
    }

    val toastMsgObserver = Observer<String> { msg ->
        Log.d(TAG, "Toast msg observer called")
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}
