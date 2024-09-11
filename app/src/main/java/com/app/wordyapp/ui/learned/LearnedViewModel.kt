package com.app.wordyapp.ui.learned

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LearnedViewModel @Inject constructor() : ViewModel() {

    private var _uiState = MutableStateFlow(LearnedUiState())
    val uiState: StateFlow<LearnedUiState> = _uiState.asStateFlow()

}