package com.languages4u.view.quiz

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.languages4u.R
import com.languages4u.databinding.FragmentQuizViewBinding
import com.languages4u.models.quiz.QuestionModel
import com.languages4u.viewmodel.quiz.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz_view.*
import kotlin.random.Random

class QuizView : Fragment() {

    private var navController: NavController? = null
    private lateinit var viewModel: QuizViewModel
    private lateinit var binding: FragmentQuizViewBinding

    //    Received Data
    private lateinit var quizId: String
    private lateinit var quizName : String
    private var totalQuestionsToAnswer: Long = 2

    //    Firebase Data
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userId: String

    //    All questions from the Quiz
    private lateinit var questions: MutableList<QuestionModel>

    //    Random questions selected from the Quiz
    private var questionToAnswer = ArrayList<QuestionModel>()

    private var questionNum = 0
    private var canAnswer = false
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var notAnswered = 0
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_view, container, false)
        viewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        binding.quizViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        firebaseAuth = FirebaseAuth.getInstance()

//        Check if user has the credentials
        if (firebaseAuth.currentUser != null) {
            userId = firebaseAuth.currentUser!!.uid
        } else {
//            TODO : Return to Auth Page
        }

//        Get the Quiz data from DetailsView
        quizId = QuizViewArgs.fromBundle(requireArguments()).quizId
        totalQuestionsToAnswer = QuizViewArgs.fromBundle(requireArguments()).totalQuestions
        quizName = QuizViewArgs.fromBundle(requireArguments()).quizName

        firebaseFirestore = FirebaseFirestore.getInstance()

//        Get questions from Firestore
        firebaseFirestore.collection("quiz-list").document(quizId)
            .collection("questions")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    questions = task.result!!.toObjects(QuestionModel::class.java)
                    pickQuestion()
                    loadUI()
                } else {
                    quiz_title.text = "Error loading quiz"
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.buttonClicked.observe(viewLifecycleOwner, buttonClickObserver)

    }

    private fun pickQuestion() {
//        Populate the list of questions to answer from all Quiz questions
//        Remove picked questions to avoid asking the same question more the once
        for (i in 0 until totalQuestionsToAnswer) {
            val randomNumber = Random.nextInt(0, questions.size)
            questionToAnswer.add(questions[randomNumber])
            questions.removeAt(randomNumber)
        }
    }

    private fun loadUI() {
        viewModel.quizTitle.value = quizName
        viewModel.question.value = "Load first Question"

        enableOptions()
        loadQuestion(0)
    }

    private fun enableOptions() {

        viewModel.optionVisibility.value = View.VISIBLE

        viewModel.nextBtn.value = "Next Question"

        viewModel.feedbackVisibility.value = View.INVISIBLE

        viewModel.nextBtnEnabled.value = false
        viewModel.nextBtnVisibility.value = View.INVISIBLE

        viewModel.optionBackground.value = requireContext().getDrawable(R.drawable.outline_light_btn_bg)

        viewModel.optionATextColor.value = requireContext().getColor(R.color.colorLightText)
        viewModel.optionBTextColor.value = requireContext().getColor(R.color.colorLightText)
        viewModel.optionCTextColor.value = requireContext().getColor(R.color.colorLightText)
    }

    private fun loadQuestion(questionNumber: Int) {
//        Populate the view
        viewModel.questionNumber.value = questionNumber.toString()
        viewModel.quizTitle.value = questionToAnswer[questionNumber].question

//        TODO: Randomize order
        viewModel.optionA.value = questionToAnswer[questionNumber].option_a
        viewModel.optionB.value = questionToAnswer[questionNumber].option_b
        viewModel.optionC.value = questionToAnswer[questionNumber].option_c

        viewModel.optionEnabled.value = true

        canAnswer = true
        questionNum = questionNumber

        startTimer(questionNumber)
    }

    private fun startTimer(questionNumber: Int) {

        val timeToAnswer = questionToAnswer[questionNumber].timer

        viewModel.questionTime.value = timeToAnswer.toString()

        viewModel.progressBarVisibility.value = View.VISIBLE

        countDownTimer = object : CountDownTimer(timeToAnswer * 1000, 10) {
            override fun onFinish() {
                canAnswer = false
                viewModel.feedback.value = "Time is up"
                viewModel.feedbackTextColor.value = requireContext().getColor(R.color.colorPrimary)

                notAnswered++
                showNextBtn()
            }

            override fun onTick(millisUntilFinished: Long) {
                quiz_question_time.text = (millisUntilFinished / 1000).toString()

                val percentage = millisUntilFinished / (timeToAnswer * 10)
                viewModel.progressBar.value = percentage.toInt()
            }
        }
        countDownTimer.start()
    }

    private val buttonClickObserver = Observer<View> { newNavi ->
        viewModel.optionEnabled.value = false
        when (newNavi!!.id) {
            R.id.quiz_option_one -> {
                verifyAnswer(quiz_option_one)
            }
            R.id.quiz_option_two -> {
                verifyAnswer(quiz_option_two)
            }
            R.id.quiz_option_three -> {
                verifyAnswer(quiz_option_three)
            }
            R.id.quiz_next_btn -> {
//                Check if all the questions were answered
                if (questionNum + 1 == totalQuestionsToAnswer.toInt()) {
                    submitResults()
                } else {
                    questionNum++
                    loadQuestion(questionNum)
                    resetOptions()
                }
            }
        }
    }

    private fun resetOptions() {
        viewModel.optionBackground.value = requireContext().getDrawable(R.drawable.outline_light_btn_bg)
        viewModel.optionATextColor.value = requireContext().getColor(R.color.colorLightText)
        viewModel.optionBTextColor.value = requireContext().getColor(R.color.colorLightText)
        viewModel.optionCTextColor.value = requireContext().getColor(R.color.colorLightText)

        viewModel.feedbackVisibility.value = View.INVISIBLE
        viewModel.nextBtnEnabled.value = false
        viewModel.nextBtnVisibility.value = View.INVISIBLE
    }

    private fun verifyAnswer(selectedAnswer: Button) {

        selectedAnswer.setTextColor(requireContext().getColor(R.color.colorDark))

        if (canAnswer) {
            if (questionToAnswer[questionNum].answer == selectedAnswer.text.toString()) {

                selectedAnswer.background =
                    requireContext().getDrawable(R.drawable.correct_answer_btn_bg)

                correctAnswers++

                viewModel.feedback.value = "Correct answer"
                viewModel.feedbackTextColor.value = requireContext().getColor(R.color.colorPrimary)
            } else {
                selectedAnswer.background =
                    requireContext().getDrawable(R.drawable.wrong_answer_btn_bg)

                wrongAnswers++

                viewModel.feedback.value = "Wrong answer \n Correct answer: " + questionToAnswer[questionNum].answer
                viewModel.feedbackTextColor.value = requireContext().getColor(R.color.colorAccent)
            }
            canAnswer = false
            countDownTimer.cancel()
            showNextBtn()
        }
    }

    private fun showNextBtn() {

        if (questionNum + 1 == totalQuestionsToAnswer.toInt()) {
            viewModel.nextBtn.value = "Submit Results"
        }

        viewModel.nextBtnEnabled.value = true
        viewModel.nextBtnVisibility.value = View.VISIBLE
    }

    private fun submitResults() {

        val data = HashMap<String, Int>()

        data["Correct"] = correctAnswers
        data["Wrong"] = wrongAnswers

//        Upload the user results to Firestore
        firebaseFirestore
            .collection("quiz-list")
            .document(quizId)
            .collection("Results")
            .document(userId)
            .set(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val action = QuizViewDirections.actionQuizViewToResultView(quizId, correctAnswers, wrongAnswers)
                    navController!!.navigate(action)
                } else {
                    quiz_title.text = task.exception!!.message
//                    TODO : Show an error
                }
            }
    }
}
