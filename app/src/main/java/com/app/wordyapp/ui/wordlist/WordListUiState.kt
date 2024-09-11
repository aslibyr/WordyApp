package com.app.wordyapp.ui.wordlist

data class WordListUiState(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
)