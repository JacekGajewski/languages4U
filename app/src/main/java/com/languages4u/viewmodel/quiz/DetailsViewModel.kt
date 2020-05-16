package com.languages4u.viewmodel.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent

class DetailsViewModel : ViewModel() {

    val navigatePage: MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }

    val title: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val description: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val difficulty: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val numberOfQuestions: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val lastScore: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun startQuizClicked() {
        navigatePage.value = NaviEvent.StartQuizPage.event
    }
}