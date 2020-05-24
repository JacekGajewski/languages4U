package com.languages4u.view

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

import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentUserSettingsBinding
import com.languages4u.viewmodel.UserSettingsViewModel

class UserSettingsView : Fragment() {
    private val TAG = "UserProfileView"


    companion object {
        fun newInstance() = UserSettingsView()
    }

    private lateinit var viewModel: UserSettingsViewModel
    private lateinit var binding: FragmentUserSettingsBinding
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_settings, container, false)
        viewModel = ViewModelProviders.of(this).get(UserSettingsViewModel::class.java)
        binding.userSettingsViewModel = viewModel
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

    private val naviObserver = Observer<String> { newNavi ->
        Log.d(TAG, "Observer called")
        when(newNavi!!) {
            NaviEvent.ProfileView.event ->
                navController!!.navigate(R.id.action_userSettingsView_to_userProfileView)
        }
    }
    private val toastMsgObserver = Observer<String> { msg ->
        Log.d(TAG, "Toast msg observer called")
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

}
