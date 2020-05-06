package com.languages4u.viewmodel.quiz

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.languages4u.data.FirebaseOperations
import com.languages4u.tools.SingleLiveEvent


class QuizViewModel : ViewModel() {

    private val TAG = "QuizViewModel"

    val quizTitle: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val questionNumber: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val progressBar: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val progressBarVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val questionTime: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //    TODO ; "Fetching data" as default text
    val question: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val optionA: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val optionATextColor: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val optionB: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val optionBTextColor: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val optionC: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val optionCTextColor: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val optionBackground: MutableLiveData<Drawable> by lazy {
        MutableLiveData<Drawable>()
    }

    val optionVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val optionEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    //    TODO ; "Verifying answers" as default text
    val feedback: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val feedbackTextColor: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val feedbackVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val nextBtn: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val nextBtnEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val nextBtnVisibility: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val buttonClicked: MutableLiveData<View> by lazy {
        SingleLiveEvent<View>()
    }

    fun onButtonClicked(view : View) {
        buttonClicked.value = view
    }
}