package com.languages4u.repository

import com.languages4u.view.quiz.QuizListModel

interface OnFirestoreQuizComplete {
    fun quizListDataAdded()
    fun onError(e : Exception?)
}