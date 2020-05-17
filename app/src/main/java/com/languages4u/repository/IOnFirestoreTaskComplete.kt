package com.languages4u.repository

import com.languages4u.models.quiz.QuizListModel

interface IOnFirestoreTaskComplete {
    fun quizListDataAdded(quizListModelsList : List<QuizListModel>)
    fun onError(e : Exception?)
}