package com.languages4u.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.languages4u.R
import com.languages4u.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuView : Fragment(), View.OnClickListener {

    private var navController: NavController? = null

    companion object {
        fun newInstance() = MenuView()
    }

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        // TODO: Use the ViewModel
        menuBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navController!!.navigate(R.id.action_menuView_to_startView)

    }
}
