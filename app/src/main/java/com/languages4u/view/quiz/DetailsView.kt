package com.languages4u.view.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentDetailsViewBinding
import com.languages4u.viewmodel.quiz.DetailsViewModel
import com.languages4u.viewmodel.quiz.QuizListViewModel
import kotlinx.android.synthetic.main.fragment_details_view.*

class DetailsView : Fragment() {

    private val TAG = "DetailsView"

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsViewBinding
    lateinit var navController: NavController
    lateinit var quizListViewModel: QuizListViewModel

    //    Quiz details
    private var quizId: String? = null
    private var quizName: String? = null
    private var position: Int? = null
    private var totalQuestions: Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details_view, container, false)
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        binding.detailsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

//        Get the position of the Quiz in RecyclerView
        position = arguments?.getInt("position")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        quizListViewModel = ViewModelProvider(this).get(QuizListViewModel::class.java)
        viewModel.navigatePage.observe(viewLifecycleOwner, naviObserver)

//        Load details of the Quiz
        quizListViewModel.quizListModel.observe(viewLifecycleOwner, Observer { quiz ->
            if (position != null) {
                Glide.with(this)
                    .load(quiz!![position!!].image)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(details_image)

//                Populate the View
                viewModel.title.value = quiz[position!!].name
                viewModel.description.value = quiz[position!!].desc
                viewModel.difficulty.value = quiz[position!!].level
                viewModel.numberOfQuestions.value = quiz[position!!].questions.toString()

//                TODO
                viewModel.lastScore.value = "N/A"

                quizId = quiz[position!!].quizid
                totalQuestions = quiz[position!!].questions
                quizName = quiz[position!!].name
            }
        })
    }

    private val naviObserver = Observer<String> { newNavi ->
        when (newNavi!!) {
            NaviEvent.StartQuizPage.event -> {
                val action = DetailsViewDirections.actionDetailsViewToQuizView(
                    quizId!!,
                    totalQuestions,
                    quizName!!
                )
                navController.navigate(action)
            }

        }
    }
}
