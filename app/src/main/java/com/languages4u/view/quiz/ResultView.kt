package com.languages4u.view.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.databinding.FragmentDetailsViewBinding
import com.languages4u.databinding.FragmentResultViewBinding
import com.languages4u.viewmodel.quiz.DetailsViewModel
import com.languages4u.viewmodel.quiz.ResultViewModel
import kotlinx.android.synthetic.main.fragment_result_view.*

class ResultView : Fragment() {

    private val TAG = "ResultView"

    private lateinit var viewModel: ResultViewModel
    private lateinit var binding: FragmentResultViewBinding
    private var navController: NavController? = null

    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore

    private lateinit var userId: String
    private var correctAnswers: Int = 0
    private var wrongAnswers: Int = 0

    var quizId: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_result_view, container, false)
        viewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
        binding.resultViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            userId = firebaseAuth.currentUser!!.uid
        } else {
//            TODO : Return to Auth Page
        }

        firebaseFirestore = FirebaseFirestore.getInstance()

        quizId = ResultViewArgs.fromBundle(requireArguments()).quizId
        correctAnswers = ResultViewArgs.fromBundle(requireArguments()).correctNum
        wrongAnswers = ResultViewArgs.fromBundle(requireArguments()).wrongNum

        results_home_btn.setOnClickListener {
            navController!!.navigate(R.id.action_resultView_to_listView)
        }

        viewModel.correctAnswers.value = correctAnswers.toString()
        viewModel.wrongAnswers.value = wrongAnswers.toString()
//        TODO
        viewModel.questionsMissed.value = "0"

        val total = correctAnswers + wrongAnswers

        if (total != 0) {
            val percent = (correctAnswers * 100) / total

            viewModel.resultPercent.value = "$percent%"
            viewModel.progressBar.value = percent
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigatePage.observe(viewLifecycleOwner, naviObserver)
    }

    private val naviObserver = Observer<String> { newNavi ->
        when (newNavi!!) {
            NaviEvent.HomePage.event -> navController!!.navigate(R.id.action_resultView_to_listView)

        }
    }
}
