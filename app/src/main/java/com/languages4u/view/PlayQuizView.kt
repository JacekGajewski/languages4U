package com.languages4u.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.languages4u.R
import com.languages4u.viewmodel.PlayQuizViewModel

class PlayQuizView : Fragment() {

    companion object {
        fun newInstance() = PlayQuizView()
    }

    private lateinit var viewModel: PlayQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_quiz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayQuizViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
