package com.example.brainbuster.helper

import com.example.brainbuster.R

object MyIconPicker {
    val icons = arrayOf(R.drawable.science_quiz,R.drawable.puzzle_quiz,R.drawable.wheel_quiz)
    var currentIcon = 0
    fun getIcon():Int{
        currentIcon = (currentIcon+1)% icons.size
        return icons[currentIcon]
    }
}