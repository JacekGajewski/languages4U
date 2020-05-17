package com.languages4u.repository

import com.languages4u.models.quiz.QuestionModel
import com.languages4u.models.quiz.QuizListModel

interface IFirebaseRepository {

    fun getQuizList(onFirestoreTaskComplete: IOnFirestoreTaskComplete)
    fun addQuiz(quiz : QuizListModel, questions : List<QuestionModel>)
    fun addQuizResult(quizId: String, userId : String, correct: Long, wrong: Long)

}