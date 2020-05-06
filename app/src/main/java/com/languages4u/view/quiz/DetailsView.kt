package com.languages4u.view.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.languages4u.R
import com.languages4u.viewmodel.quiz.QuizListViewModel
import kotlinx.android.synthetic.main.fragment_details_view.*

class DetailsView : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var quizListViewModel : QuizListViewModel

//    Quiz details
    private var quizId : String? = null
    private var quizName : String? = null
    private var position : Int? = null
    private var totalQuestions : Long = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

//        Get the position of the Quiz in RecyclerView
        position = arguments?.getInt("position")

        details_start_btn.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        quizListViewModel = ViewModelProvider(this).get(QuizListViewModel::class.java)

//        Load details of the Quiz
        quizListViewModel.quizListModel.observe(viewLifecycleOwner, Observer { quiz ->
            if (position != null) {
                Glide.with(this)
                    .load(quiz!![position!!].image)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_image)
                    .into(details_image)

//                Populate the View
                details_title.text = quiz[position!!].name
                details_desc.text = quiz[position!!].desc
                details_difficulty_text.text = quiz[position!!].level
                details_questions_text.text = quiz[position!!].questions.toString()

                quizId = quiz[position!!].quizid
                totalQuestions = quiz[position!!].questions
                quizName = quiz[position!!].name
            }
        })
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            details_start_btn.id -> {
                val action = DetailsViewDirections.actionDetailsViewToQuizView(quizId!!, totalQuestions, quizName!!)
                navController.navigate(action)
            }
        }
    }
}
