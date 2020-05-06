package com.languages4u.view.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.languages4u.R
import kotlinx.android.synthetic.main.fragment_result_view.*

class ResultView : Fragment() {

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
        return inflater.inflate(R.layout.fragment_result_view, container, false)
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

        results_correct_text.text = correctAnswers.toString()
        results_wrong_text.text = wrongAnswers.toString()
        results_missed_text.text = ""

        val total = correctAnswers + wrongAnswers

        if (total != 0){
            val percent = (correctAnswers * 100)/total

            results_percent.text = "$percent%"
            results_progress.progress = percent
        }

//        firebaseFirestore.collection("quiz-list")
//            .document(quizId!!)
//            .collection("Results")
//            .document(userId)
//            .get()
//            .addOnCompleteListener {task ->
//                if (task.isSuccessful) {
//                    var result = task.result
//
//                    val correct : Long? = result!!.getLong("Correct")
//                    results_correct_text.text = correct!!.toString()
//                    val wrong : Long? = result.getLong("Wrong")
//                    results_wrong_text.text = wrong!!.toString()
//                    results_missed_text.text = ""
//
//                    var total : Long = correct + wrong
//                    var procent = (correct * 100)/total
//
//                    results_percent.text = "$procent%"
//                    results_progress.progress = procent.toInt()
//                }
//            }

    }
}
