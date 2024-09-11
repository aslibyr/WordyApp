package com.app.wordyapp.data

data class Word(
    val word: String,
    val meaning: String,
    val imageUrl: String,
    var isLearned: Boolean = false
)
