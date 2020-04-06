package com.languages4u.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.languages4u.R
import com.languages4u.viewmodel.ChooseQuizViewModel

class ChooseQuizView : Fragment() {

    companion object {
        fun newInstance() = ChooseQuizView()
    }

    private lateinit var viewModel: ChooseQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_choose_quiz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChooseQuizViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
