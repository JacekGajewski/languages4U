package com.languages4u.repository

import com.languages4u.view.quiz.QuizListModel

interface OnFirestoreTaskComplete {
    fun quizListDataAdded(quizListModelsList : List<QuizListModel>)
    fun onError(e : Exception?)
}