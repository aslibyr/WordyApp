package com.app.wordy.data

data class Word(
    val word: String,
    val meaning: String,
    val imageUrl: String,
    var isLearned: Boolean = false
)
