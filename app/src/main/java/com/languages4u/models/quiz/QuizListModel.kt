package com.languages4u.models.quiz

import com.google.firebase.firestore.DocumentId

class QuizListModel() {

    @DocumentId
    lateinit var quizid : String
    lateinit var name : String
    lateinit var desc : String
    lateinit var image : String
    lateinit var level : String
    lateinit var visibilty : String
    var questions : Long = 0

    constructor(
        name: String,
        desc: String,
        image: String,
        level: String,
        visibility: String,
        questions: Long
    ) : this() {
        this.name = name
        this.desc = desc
        this.image = image
        this.level = level
        this.visibilty = visibility
        this.questions = questions
    }

}