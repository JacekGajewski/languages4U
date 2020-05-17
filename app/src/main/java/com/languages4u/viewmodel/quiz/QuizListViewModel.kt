package com.languages4u.viewmodel.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.repository.FirebaseRepository
import com.languages4u.models.quiz.QuizListModel
import com.languages4u.repository.IOnFirestoreTaskComplete

class QuizListViewModel() : ViewModel(), IOnFirestoreTaskComplete {

    val quizListModel : MutableLiveData<List<QuizListModel>> by lazy {
        MutableLiveData<List<QuizListModel>>()
    }

    var firebaseRepository : FirebaseRepository = FirebaseRepository()

    init {
        firebaseRepository.getQuizList(this)
    }

    override fun quizListDataAdded(quizListModelsList: List<QuizListModel>) {
        quizListModel.value = quizListModelsList
    }

    override fun onError(e: Exception?) {
        TODO("Not yet implemented")
    }
}