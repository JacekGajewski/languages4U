package com.languages4u.viewmodel.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.languages4u.data.NaviEvent
import com.languages4u.tools.SingleLiveEvent

class ResultViewModel : ViewModel() {

    val navigatePage: MutableLiveData<String> by lazy {
        SingleLiveEvent<String>()
    }

    val progressBar: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val resultPercent: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val correctAnswers: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val wrongAnswers: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val questionsMissed: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun goHomeClicked() {
        navigatePage.value = NaviEvent.HomePage.event
    }
}