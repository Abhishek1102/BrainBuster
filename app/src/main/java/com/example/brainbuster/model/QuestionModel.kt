package com.example.brainbuster.model

data class QuestionModel(
    var description: String = "",
    var option1: String = "",
    var option2: String = "",
    var option3: String = "",
    var option4: String = "",
    var answer: String = "",
    var userAnswer: String = ""
)
