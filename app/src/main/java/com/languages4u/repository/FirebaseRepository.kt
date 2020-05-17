package com.languages4u.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.languages4u.models.quiz.QuestionModel
import com.languages4u.models.quiz.QuizListModel

class FirebaseRepository : IFirebaseRepository {
    val TAG = "FirebaseRepository"

    private val mQuizCollectionName = "quiz-list"
    private val mQuestionsCollectionName = "questions"
    private val mResultsCollectionName = "Results"

    val firebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val quizRef by lazy {
        firebaseFirestore.collection(mQuizCollectionName)
            .whereEqualTo("visibility", "public")
    }

    val quizAddRef by lazy {
        firebaseFirestore.collection(mQuizCollectionName)
    }

    override fun getQuizList(onFirestoreTaskComplete: IOnFirestoreTaskComplete) {
        quizRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onFirestoreTaskComplete.quizListDataAdded(task.result!!.toObjects(QuizListModel::class.java))
            } else {
                onFirestoreTaskComplete.onError(task.exception)
            }
        }
    }

    override fun addQuiz(quiz: QuizListModel, questions : List<QuestionModel>) {

        val quiz_json = hashMapOf(
            "desc" to quiz.desc,
            "image" to quiz.image,
            "level" to quiz.level,
            "name" to quiz.name,
            "questions" to quiz.questions,
            "visibility" to quiz.visibilty
        )

        var quizId = ""

        quizAddRef
            .add(quiz_json)
            .addOnSuccessListener { documentReference ->
                Log.i(TAG, "Quiz added successfully")
                quizId = documentReference.id
                for(question in questions) {
                    val question_json = hashMapOf(
                        "answer" to question.answer,
                        "option_a" to question.option_a,
                        "option_b" to question.option_b,
                        "option_c" to question.option_c,
                        "question" to question.question,
                        "timer" to question.timer
                    )

                    quizAddRef
                        .document(quizId)
                        .collection(mQuestionsCollectionName)
                        .add(question_json)
                        .addOnSuccessListener { Log.i(TAG, "Quiz question added successfully") }
                        .addOnFailureListener { e -> Log.e(TAG, "Error while adding quiz question", e) }
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding quiz", e)
            };
    }

    override fun addQuizResult(quizId: String, userId : String, correct: Long, wrong: Long) {
        val result = hashMapOf(
            "Correct" to correct,
            "Wrong" to wrong
        )

        quizAddRef
            .document(quizId)
            .collection(mResultsCollectionName)
            .document(userId)
            .set(result)
            .addOnSuccessListener { Log.i(TAG, "Quiz result added successfully") }
            .addOnFailureListener { e -> Log.e(TAG, "Error while adding quiz result", e) }
    }
}