package com.example.brainbuster.model

data class QuizModel(
    var id: String = "",
    var title: String = "",
    var questions: MutableMap<String, QuestionModel> = mutableMapOf()
)
