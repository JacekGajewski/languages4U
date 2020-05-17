package com.languages4u.viewmodel.quiz

import android.util.Log
import android.view.View
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

    val firebaseRepository : IFirebaseRepository by lazy {
        FirebaseRepository()
    }

    val addQuizBtnEnabled : MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    val toastMsg : MutableLiveData<String> by lazy {
        MutableLiveData("")
    }

    val navigatePage: MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }

    val quizQuestionsList : MutableLiveData<MutableList<QuestionModel>> by lazy {
        MutableLiveData(mutableListOf<QuestionModel>())
    }

    val questionsNumber : MutableLiveData<String> by lazy {
        MutableLiveData("0")
    }

    val quizName : MutableLiveData<String> by lazy {
        MutableLiveData("Quiz name vm")
    }

    val quizDesc : MutableLiveData<String> by lazy {
        MutableLiveData("Quiz description")
    }

    val quizDifficulty : MutableLiveData<String> by lazy {
        MutableLiveData("Beginner")
    }

    val quizVisibility : MutableLiveData<String> by lazy {
        MutableLiveData("Public")
    }

    // Question data

    val question : MutableLiveData<String> by lazy {
        MutableLiveData("Question")
    }

    val answerA : MutableLiveData<String> by lazy {
        MutableLiveData("Answer A")
    }

    val answerB : MutableLiveData<String> by lazy {
        MutableLiveData("Answer B")
    }

    val answerC : MutableLiveData<String> by lazy {
        MutableLiveData("Answer C")
    }

    val correctAnswer : MutableLiveData<String> by lazy {
        MutableLiveData("Answer A")
    }

    val timer : MutableLiveData<String> by lazy {
        MutableLiveData("5")
    }

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

        val quiz = QuizListModel(quizName.value!!, quizDesc.value!!, "No Image added yet(TODO)",
            quizDifficulty.value!!, quizVisibility.value!!, quizQuestionsList.value!!.size.toLong())

        firebaseRepository.addQuiz(quiz, quizQuestionsList.value!!)

    }

    fun onRadioBtnDifficultyClicked(view : View) {
        when(view.id) {
            R.id.addquiz_rb_beginner -> quizDifficulty.value = QuizProperties.Begginner.value
            R.id.addquiz_rb_intermediate -> quizDifficulty.value = QuizProperties.Intermediate.value
            R.id.addquiz_rb_advanced -> quizDifficulty.value = QuizProperties.Advanced.value
        }
    }

    fun onRadioBtnVisibilityClicked(view : View) {
        when(view.id) {
            R.id.addquiz_rb_public -> quizVisibility.value = QuizProperties.Public.value
            R.id.addquiz_rb_private -> quizVisibility.value = QuizProperties.Private.value
        }
    }

    fun onRadioBtnCorrectAnswerClicked(view : View) {
        when(view.id) {
            R.id.addquestion_rb_A -> correctAnswer.value = "A"
            R.id.addquestion_rb_B -> correctAnswer.value = "B"
            R.id.addquestion_rb_C -> correctAnswer.value = "C"
        }
    }

    fun onAddQuizQuestionClicked() {
        navigatePage.value = NaviEvent.AddQuestion.event
    }

    fun onAddQuestionClicked() {

        // TODO verify properties

        var correctAns = "A"

        when(correctAnswer.value) {
            "A" -> correctAns = answerA.value!!
            "B" -> correctAns = answerB.value!!
            "C" -> correctAns = answerC.value!!
        }

        val quest = QuestionModel(question.value!!, answerA.value!!, answerB.value!!, answerC.value!!,
            correctAns, timer.value!!.toLong())

        quizQuestionsList.value!!.add(quest)
        Log.d(TAG, "List size (question): " + quizQuestionsList.value!!.size.toString())
        updateQuestionNumber()
        navigatePage.value = NaviEvent.AddQuiz.event
    }
}
