package com.languages4u.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentAuthBinding
import com.languages4u.databinding.FragmentSignInBinding
import com.languages4u.viewmodel.AuthViewModel
import com.languages4u.viewmodel.SignInViewModel

class SignInView : Fragment() {
    private val TAG = "SignInView"

    companion object {
        fun newInstance() = SignInView()
    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: FragmentSignInBinding
    var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        binding.singInViewModel = viewModel
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
            NaviEvent.MenuPage.event -> navController!!.navigate(R.id.action_signInView_to_menuView)
        }
    }

}
