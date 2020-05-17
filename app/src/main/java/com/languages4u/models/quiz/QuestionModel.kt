package com.languages4u.models.quiz

import com.google.firebase.firestore.DocumentId

class QuestionModel() {

    @DocumentId
    private lateinit var quidId : String
    lateinit var question : String
    lateinit var option_a : String
    lateinit var option_b : String
    lateinit var option_c : String
    lateinit var answer : String
    var timer : Long = 0

    constructor(
        question: String,
        option_a: String,
        option_b: String,
        option_c: String,
        answer: String,
        timer: Long
    ) : this() {
        this.question = question
        this.option_a = option_a
        this.option_b = option_b
        this.option_c = option_c
        this.answer = answer
        this.timer = timer
    }
}