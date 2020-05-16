package com.languages4u.viewmodel.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.repository.FirebaseRepository
import com.languages4u.view.quiz.QuizListModel

class QuizListViewModel() : ViewModel(), FirebaseRepository.OnFirestoreTaskComplete {

    val quizListModel : MutableLiveData<List<QuizListModel>> by lazy {
        MutableLiveData<List<QuizListModel>>()
    }

    var firebaseRepository : FirebaseRepository = FirebaseRepository(this)

    init {
        firebaseRepository.getQuizListData()
    }

    override fun quizListDataAdded(quizListModelsList: List<QuizListModel>) {
        quizListModel.value = quizListModelsList
    }

    override fun onError(e: Exception?) {
        TODO("Not yet implemented")
    }
}