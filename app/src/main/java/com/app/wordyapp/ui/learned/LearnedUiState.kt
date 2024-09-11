package com.app.wordyapp.ui.learned

data class LearnedUiState(
    val isLoading: Boolean = false,
    val list: List<String> = emptyList(),
)