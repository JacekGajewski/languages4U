package com.languages4u.view.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
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
import kotlinx.android.synthetic.main.fragment_add_quiz.*


class AddQuizView : Fragment() {
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

        if (spinner != null) {

            spinner.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parentView: AdapterView<*>?,
                    selectedItemView: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d("DUPA", position.toString())
                    var language =
                        "https://firebasestorage.googleapis.com/v0/b/languages4u-fdf6b.appspot.com/o/quiz-covers%2Fenglish.jpeg?alt=media&token=1340e188-fe53-445a-bf9e-137c256fef80"
                    when (position) {
                        1 -> language =
                            "https://firebasestorage.googleapis.com/v0/b/languages4u-fdf6b.appspot.com/o/quiz-covers%2Fgerman.jpg?alt=media&token=325a8fa1-ef72-4b73-b907-7d80216ee1ed"
                        2 -> language =
                            "https://firebasestorage.googleapis.com/v0/b/languages4u-fdf6b.appspot.com/o/quiz-covers%2Frussian.jpg?alt=media&token=606a7249-236a-4c28-b365-1aa8c8ad4236"
                        3 -> language =
                            "https://firebasestorage.googleapis.com/v0/b/languages4u-fdf6b.appspot.com/o/quiz-covers%2Fspanish.png?alt=media&token=ab87209b-ac80-4601-a844-2fe13711c37b"
                        4 -> language =
                            "https://firebasestorage.googleapis.com/v0/b/languages4u-fdf6b.appspot.com/o/quiz-covers%2Fchinese.jpg?alt=media&token=0c60aa5e-e3e5-4444-86eb-9381a18ba06b"

                    }
                    viewModel.spinnerValue = language
                }
            }
        }
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
