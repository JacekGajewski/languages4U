package com.languages4u.view.quiz

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
import com.languages4u.databinding.FragmentAddQuizBinding
import com.languages4u.viewmodel.quiz.AddQuizViewModel


class AddQuizView: Fragment() {
    private val TAG = "AddQuizView"

    companion object {
        fun newInstance() = AddQuizView()
    }

    private lateinit var viewModel: AddQuizViewModel
    private lateinit var binding: FragmentAddQuizBinding
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_quiz, container, false)
        viewModel = ViewModelProviders.of(activity!!).get(AddQuizViewModel::class.java)
        binding.addQuizViewModel = viewModel
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
        when (newNavi!!) {
            NaviEvent.HomePage.event -> navController!!.navigate(R.id.action_addQuizView_to_startView)
            NaviEvent.AddQuestion.event -> navController!!.navigate(R.id.action_addQuizView_to_addQuestionView)
        }
    }

    private val toastMsgObserver = Observer<String> { msg ->
        Log.d(TAG, "Toast msg observer called")
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
}