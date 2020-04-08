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
import com.languages4u.databinding.FragmentPassRecoveryBinding
import com.languages4u.databinding.FragmentSignInBinding
import com.languages4u.viewmodel.PasswordRecoveryViewModel
import com.languages4u.viewmodel.SignInViewModel

class PasswordRecoveryView : Fragment() {
    private val TAG = "PasswordRecoveryView"

    companion object {
        fun newInstance() = PasswordRecoveryView()
    }

    private lateinit var viewModel: PasswordRecoveryViewModel
    private lateinit var binding: FragmentPassRecoveryBinding
    var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pass_recovery, container, false)
        viewModel = ViewModelProviders.of(this).get(PasswordRecoveryViewModel::class.java)
        binding.passwordRecoveryViewModel = viewModel
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
            NaviEvent.ForgotPass.event -> navController!!.navigate(R.id.action_passwordRecoveryView_to_navi_graph)
        }
    }

}
