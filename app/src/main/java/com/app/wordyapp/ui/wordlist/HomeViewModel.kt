package com.app.wordyapp.ui.wordlist

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.app.wordyapp.data.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(private val sharedPreferences: SharedPreferences) :
    ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    var words: StateFlow<List<Word>> = _words

    fun updateLearnedWords(newWords: List<Word>) {
        _words.value = newWords
    }

    fun shuffleWords() {
        val shuffledList = _words.value.shuffled(Random(System.currentTimeMillis()))
        _words.value = shuffledList
    }


    fun saveLearnedWord(word: Word) {
        val sharedPreferences = sharedPreferences
        val editor = sharedPreferences.edit()

        editor.putBoolean(word.word, true)
        editor.apply()
        _words.value = _words.value.map {
            if (it.word == word.word) {
                it.copy(isLearned = true)
            } else {
                it
            }
        }
    }

    fun isSavedLearned(word: Word): Boolean {
        return sharedPreferences.getBoolean(word.word, false)
    }

}