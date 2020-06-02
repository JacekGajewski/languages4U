package com.languages4u.viewmodel.quiz

import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.languages4u.R
import com.languages4u.data.NaviEvent
import com.languages4u.data.QuizProperties
import com.languages4u.data.ToastEvent
import com.languages4u.models.quiz.QuestionModel
import com.languages4u.models.quiz.QuizListModel
import com.languages4u.repository.IFirebaseRepository
import com.nhaarman.mockitokotlin2.anyOrNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*


class AddQuizViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var addQuizViewModel: AddQuizViewModel

    @Mock
    lateinit var firebase : IFirebaseRepository

    @Mock
    lateinit var context : Context

    @Mock
    lateinit var view : View

    @Captor
    private lateinit var captor1: ArgumentCaptor<QuizListModel>

    @Captor
    private lateinit var captor2: ArgumentCaptor<List<QuestionModel>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.addQuizViewModel = AddQuizViewModel()
    }

    @Test
    fun test_onAddQuizClicked() {
        addQuizViewModel.firebaseRepository = firebase

        // check add quiz with no questions
        addQuizViewModel.onAddQuizClicked()
        assert(addQuizViewModel.toastMsg.value == ToastEvent.NoQuestionAdded.event)

        val question = QuestionModel(
            "Question", "A", "B", "C","A",5)

        addQuizViewModel.quizQuestionsList.value?.add(question)

        // no quiz name
        addQuizViewModel.quizName.value = ""
        addQuizViewModel.onAddQuizClicked()
        assert(addQuizViewModel.toastMsg.value == ToastEvent.NoQuizName.event)

        val quizName = "name"
        val quizDesc = "desc"
        val quizImage = "noimg"
        val quizDiff = "quizDiff"
        val quizVis = "Public"


        addQuizViewModel.quizName.value = quizName
        addQuizViewModel.onAddQuizClicked()
        Mockito.verify(firebase).addQuiz(anyOrNull(), Mockito.anyList())
    }

    @Test
    fun test_updateQuestion() {
        val question = QuestionModel(
            "Question", "A", "B", "C","A",5)
        addQuizViewModel.quizQuestionsList.value?.add(question)
        addQuizViewModel.updateQuestionNumber()

        val str = addQuizViewModel.quizQuestionsList.value!!.size.toString()
        assert(addQuizViewModel.questionsNumber.value == str)
    }

    @Test
    fun test_onRadioBtnDifficultyClicked() {

        Mockito.`when`(view.id).thenReturn(R.id.addquiz_rb_beginner)
        addQuizViewModel.onRadioBtnDifficultyClicked(view)
        assert(addQuizViewModel.quizDifficulty.value == QuizProperties.Begginner.value)

        Mockito.`when`(view.id).thenReturn(R.id.addquiz_rb_intermediate)
        addQuizViewModel.onRadioBtnDifficultyClicked(view)
        assert(addQuizViewModel.quizDifficulty.value == QuizProperties.Intermediate.value)

        Mockito.`when`(view.id).thenReturn(R.id.addquiz_rb_advanced)
        addQuizViewModel.onRadioBtnDifficultyClicked(view)
        assert(addQuizViewModel.quizDifficulty.value == QuizProperties.Advanced.value)
    }

    @Test
    fun test_onRadioBtnVisibilityClicked() {

        Mockito.`when`(view.id).thenReturn(R.id.addquiz_rb_public)
        addQuizViewModel.onRadioBtnVisibilityClicked(view)
        assert(addQuizViewModel.quizVisibility.value == QuizProperties.Public.value)

        Mockito.`when`(view.id).thenReturn(R.id.addquiz_rb_private)
        addQuizViewModel.onRadioBtnVisibilityClicked(view)
        assert(addQuizViewModel.quizVisibility.value == QuizProperties.Private.value)
    }

    @Test
    fun test_onRadioBtnCorrectAnswerClicked() {

        Mockito.`when`(view.id).thenReturn(R.id.addquestion_rb_A)
        addQuizViewModel.onRadioBtnCorrectAnswerClicked(view)
        assert(addQuizViewModel.correctAnswer.value == "A")

        Mockito.`when`(view.id).thenReturn(R.id.addquestion_rb_B)
        addQuizViewModel.onRadioBtnCorrectAnswerClicked(view)
        assert(addQuizViewModel.correctAnswer.value == "B")

        Mockito.`when`(view.id).thenReturn(R.id.addquestion_rb_C)
        addQuizViewModel.onRadioBtnCorrectAnswerClicked(view)
        assert(addQuizViewModel.correctAnswer.value == "C")
    }

    @Test
    fun test_onAddQuizQuestionClicked() {
        addQuizViewModel.onAddQuizQuestionClicked()
        assert(addQuizViewModel.navigatePage.value == NaviEvent.AddQuestion.event)
    }

    @Test
    fun test_onAddQuestionClicked() {
        addQuizViewModel.question.value = ""
        addQuizViewModel.onAddQuestionClicked()
        assert(addQuizViewModel.toastMsg.value == ToastEvent.NoQuestion.event)

        addQuizViewModel.question.value = "question"
        addQuizViewModel.answerA.value = ""
        addQuizViewModel.answerB.value = "B"
        addQuizViewModel.answerC.value = "C"

        addQuizViewModel.onAddQuestionClicked()
        assert(addQuizViewModel.toastMsg.value == ToastEvent.NoAnswer.event)

        addQuizViewModel.answerA.value = "A"

        var size = addQuizViewModel.quizQuestionsList.value!!.size + 1

        addQuizViewModel.correctAnswer.value = "A"

        addQuizViewModel.onAddQuestionClicked()
        assert(addQuizViewModel.quizQuestionsList.value?.size == size)
        assert(addQuizViewModel.navigatePage.value == NaviEvent.AddQuiz.event)

        size = addQuizViewModel.quizQuestionsList.value!!.size + 1

        addQuizViewModel.correctAnswer.value = "B"

        addQuizViewModel.onAddQuestionClicked()
        assert(addQuizViewModel.quizQuestionsList.value?.size == size)
        assert(addQuizViewModel.navigatePage.value == NaviEvent.AddQuiz.event)

        size = addQuizViewModel.quizQuestionsList.value!!.size + 1

        addQuizViewModel.correctAnswer.value = "C"

        addQuizViewModel.onAddQuestionClicked()
        assert(addQuizViewModel.quizQuestionsList.value?.size == size)
        assert(addQuizViewModel.navigatePage.value == NaviEvent.AddQuiz.event)
    }

    @Test
    fun test_clear() {
        addQuizViewModel
        addQuizViewModel.quizName.value = "qwe"
        addQuizViewModel.quizDesc.value = "qwe"
        addQuizViewModel.question.value = "wqe"
        addQuizViewModel.questionsNumber.value = "qwe"
        addQuizViewModel.answerA.value = "qwe"
        addQuizViewModel.answerB.value = "qwe"
        addQuizViewModel.answerC.value = "qwe"

        addQuizViewModel.clear()

        assert(addQuizViewModel.quizName.value == "" &&
                addQuizViewModel.quizDesc.value == "" &&
                addQuizViewModel.question.value == "" &&
                addQuizViewModel.questionsNumber.value == "" &&
                addQuizViewModel.answerA.value == "" &&
                addQuizViewModel.answerB.value == "" &&
                addQuizViewModel.answerC.value == "" &&
                addQuizViewModel.quizQuestionsList.value?.size == 0)
    }

    @Test
    fun test_onClearClicked() {
        addQuizViewModel
        addQuizViewModel.quizName.value = "qwe"
        addQuizViewModel.quizDesc.value = "qwe"
        addQuizViewModel.question.value = "wqe"
        addQuizViewModel.questionsNumber.value = "qwe"
        addQuizViewModel.answerA.value = "qwe"
        addQuizViewModel.answerB.value = "qwe"
        addQuizViewModel.answerC.value = "qwe"

        addQuizViewModel.onClearClicked()

        assert(addQuizViewModel.quizName.value == "" &&
                addQuizViewModel.quizDesc.value == "" &&
                addQuizViewModel.question.value == "" &&
                addQuizViewModel.questionsNumber.value == "" &&
                addQuizViewModel.answerA.value == "" &&
                addQuizViewModel.answerB.value == "" &&
                addQuizViewModel.answerC.value == "" &&
                addQuizViewModel.quizQuestionsList.value?.size == 0)
    }
}