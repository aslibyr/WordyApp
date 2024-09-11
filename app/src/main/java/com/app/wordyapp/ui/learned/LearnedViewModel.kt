package com.app.wordyapp.ui.learned

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.app.wordyapp.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LearnedViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    fun updateLearnedWords(newWords: List<Word>) {
        val learnedWords = newWords.map {
            it.copy(isLearned = isSavedLearned(it))
        }.filter { it.isLearned }
        _words.value = learnedWords
    }

    fun isSavedLearned(word: Word): Boolean {
        return sharedPreferences.getBoolean(word.word, false)
    }

    fun removeLearnedWord(word: Word) {
        val sharedPreferences = sharedPreferences
        val editor = sharedPreferences.edit()
        editor.putBoolean(word.word, false)
        editor.apply()
        _words.value = _words.value.map {
            if (it.word == word.word) {
                it.copy(isLearned = false)
            } else {
                it
            }
        }
    }
}