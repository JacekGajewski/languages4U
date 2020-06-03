package com.languages4u.viewmodel.quiz

import android.util.Log
import android.view.View
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.data.QuizProperties
import com.languages4u.data.ToastEvent
import com.languages4u.models.quiz.QuestionModel
import com.languages4u.models.quiz.QuizListModel
import com.languages4u.repository.FirebaseRepository
import com.languages4u.repository.IFirebaseRepository
import com.languages4u.tools.SingleLiveEvent

class AddQuizViewModel : ViewModel() {

    val TAG = "AddQuizViewModel"

    val firebaseRepository: IFirebaseRepository by lazy {
        FirebaseRepository()
    }

    val toastMsg: MutableLiveData<String> by lazy {
        MutableLiveData("")
    }

    val navigatePage: MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }

    val quizQuestionsList: MutableLiveData<MutableList<QuestionModel>> by lazy {
        MutableLiveData(mutableListOf<QuestionModel>())
    }

    val questionsNumber: MutableLiveData<String> by lazy {
        MutableLiveData("0")
    }

    val quizName: MutableLiveData<String> by lazy {
        MutableLiveData("Quiz name")
    }

    val quizDesc: MutableLiveData<String> by lazy {
        MutableLiveData("Quiz description")
    }

    val quizDifficulty: MutableLiveData<String> by lazy {
        MutableLiveData("Beginner")
    }

    val quizVisibility: MutableLiveData<String> by lazy {
        MutableLiveData("Public")
    }

    // Question data

    val question: MutableLiveData<String> by lazy {
        MutableLiveData("Question")
    }

    val answerA: MutableLiveData<String> by lazy {
        MutableLiveData("Answer A")
    }

    val answerB: MutableLiveData<String> by lazy {
        MutableLiveData("Answer B")
    }

    val answerC: MutableLiveData<String> by lazy {
        MutableLiveData("Answer C")
    }

    val correctAnswer: MutableLiveData<String> by lazy {
        MutableLiveData("Answer A")
    }

    val timer: MutableLiveData<String> by lazy {
        MutableLiveData("5")
    }

    val entries: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

    var spinnerValue = "English"

    fun updateQuestionNumber() {
        questionsNumber.value = quizQuestionsList.value!!.size.toString()
    }

    fun onAddQuizClicked() {

        Log.d(TAG, "List size (quiz): " + quizQuestionsList.value!!.size.toString())
        if(quizQuestionsList.value!!.size == 0) {
            toastMsg.value = ToastEvent.NoQuestionAdded.event
            return
        }

        // TODO validate quiz properties
        val quiz = QuizListModel(quizName.value!!, quizDesc.value!!, spinnerValue,
            quizDifficulty.value!!, quizVisibility.value!!, quizQuestionsList.value!!.size.toLong())

        firebaseRepository.addQuiz(quiz, quizQuestionsList.value!!)

    }

    fun onRadioBtnDifficultyClicked(view: View) {
        when (view.id) {
            R.id.addquiz_rb_beginner -> quizDifficulty.value = QuizProperties.Begginner.value
            R.id.addquiz_rb_intermediate -> quizDifficulty.value = QuizProperties.Intermediate.value
            R.id.addquiz_rb_advanced -> quizDifficulty.value = QuizProperties.Advanced.value
        }
    }

    fun onRadioBtnVisibilityClicked(view: View) {
        when (view.id) {
            R.id.addquiz_rb_public -> quizVisibility.value = QuizProperties.Public.value
            R.id.addquiz_rb_private -> quizVisibility.value = QuizProperties.Private.value
        }
    }

    fun onRadioBtnCorrectAnswerClicked(view: View) {
        when (view.id) {
            R.id.addquestion_rb_A -> correctAnswer.value = "A"
            R.id.addquestion_rb_B -> correctAnswer.value = "B"
            R.id.addquestion_rb_C -> correctAnswer.value = "C"
        }
    }

    fun onAddQuizQuestionClicked() {
        navigatePage.value = NaviEvent.AddQuestion.event
    }

    fun onClearClicked() {
        clear()
    }

    fun onAddQuestionClicked() {

        // TODO verify properties

        Log.d(TAG, "Answer a:" + answerA.value)

        var correctAns = "A"

        when (correctAnswer.value) {
            "A" -> correctAns = answerA.value!!
            "B" -> correctAns = answerB.value!!
            "C" -> correctAns = answerC.value!!
        }

        Log.d(TAG, correctAnswer.value + " nx:" + correctAns)

        val quest = QuestionModel(
            question.value!!, answerA.value!!, answerB.value!!, answerC.value!!,
            correctAns, timer.value!!.toLong()
        )

        quizQuestionsList.value!!.add(quest)
        Log.d(TAG, "List size (question): " + quizQuestionsList.value!!.size.toString())
        updateQuestionNumber()
        navigatePage.value = NaviEvent.AddQuiz.event
    }

    fun clear() {
        quizQuestionsList.value!!.clear()
        quizName.value = "Quiz name"
        quizDesc.value = "Quiz description"
        question.value = "Question"
        questionsNumber.value = "0"
        answerA.value = "Answer A"
        answerB.value = "Answer B"
        answerC.value = "Answer C"
    }

    init {
        val list = mutableListOf("english", "german")
        entries.value = list
    }
}