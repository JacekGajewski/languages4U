package com.languages4u.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.languages4u.view.quiz.QuizListModel

class FirebaseRepository(var onFirestoreTaskComplete: OnFirestoreTaskComplete) {

    var firebaseFirestore = FirebaseFirestore.getInstance()

//    Don't load private quiz
    var quizRef = firebaseFirestore.collection("quiz-list")
        .whereEqualTo("visibility", "public")

    fun getQuizListData() {
        quizRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onFirestoreTaskComplete.quizListDataAdded(task.result!!.toObjects(QuizListModel::class.java))
            } else {
                onFirestoreTaskComplete.onError(task.exception)
            }
        }
    }

    interface OnFirestoreTaskComplete {
        fun quizListDataAdded(quizListModelsList : List<QuizListModel>)
        fun onError(e : Exception?)
    }
}