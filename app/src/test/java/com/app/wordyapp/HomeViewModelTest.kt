package com.app.wordyapp.ui.wordlist

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.wordyapp.data.Word
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.mock

class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        sharedPreferences = mock(SharedPreferences::class.java)
        viewModel = HomeViewModel(sharedPreferences)
    }

    @Test
    fun `updateLearnedWords should update the word list`() = runTest {
        val words = listOf(
            Word(
                "Dog",
                "Köpek",
                "https://img.freepik.com/premium-photo/3d-dog-frisbee-air-icon-exciting-pet-game-moment-illustration-logo_762678-105051.jpg",
                isLearned = true
            ),
            Word(
                "Cat",
                "Kedi",
                "https://img.freepik.com/premium-photo/3d-cat-with-balloon-icon-feline-fun-with-floating-toy-illustration-logo_762678-103833.jpg",
                isLearned = false
            )
        )

        viewModel.updateLearnedWords(words)

        assertEquals(2, viewModel.words.first().size)
        assertEquals("Dog", viewModel.words.first()[0].word)
        assertEquals("Cat", viewModel.words.first()[1].word)
    }

    @Test
    fun `shuffleWords should shuffle the word list`() = runTest {
        val words = listOf(
            Word(
                "Dog",
                "Köpek",
                "https://img.freepik.com/premium-photo/3d-dog-frisbee-air-icon-exciting-pet-game-moment-illustration-logo_762678-105051.jpg",
                isLearned = true
            ),
            Word(
                "Cat",
                "Kedi",
                "https://img.freepik.com/premium-photo/3d-cat-with-balloon-icon-feline-fun-with-floating-toy-illustration-logo_762678-103833.jpg",
                isLearned = false
            ),
            Word(
                "Tree",
                "Ağaç",
                "https://img.freepik.com/premium-photo/3d-tree-icon-nature-environment-logo-illustration_762678-35794.jpg?ga=GA1.1.1432797158.1700920391&semt=ais_hybrid",
                isLearned = false
            )
        )

        viewModel.updateLearnedWords(words)

        val originalList = viewModel.words.first()

        // Perform the shuffle operation multiple times to ensure the shuffle is effective
        var isShuffled = false
        repeat(10) {
            viewModel.shuffleWords()
            val shuffledList = viewModel.words.first()
            if (originalList != shuffledList) {
                isShuffled = true
                return@repeat
            }
        }

        assert(isShuffled) { "The word list was not shuffled after multiple attempts" }
    }


    @Test
    fun `saveLearnedWord should save word as learned and update the list`() = runTest {
        val word = Word(
            "House",
            "Ev",
            "https://img.freepik.com/free-psd/3d-house-property-illustration_23-2151682346.jpg",
            isLearned = false
        )

        // Prepare mock editor
        val editor = mock(SharedPreferences.Editor::class.java)
        Mockito.`when`(sharedPreferences.edit()).thenReturn(editor)

        viewModel.updateLearnedWords(listOf(word))
        viewModel.saveLearnedWord(word)

        Mockito.verify(editor).putBoolean(eq("House"), eq(true))
        Mockito.verify(editor).apply()

        assertEquals(true, viewModel.words.first().find { it.word == "House" }?.isLearned)
    }

    @Test
    fun `isSavedLearned should return correct saved state`() {
        val word = Word(
            "House",
            "Ev",
            "https://img.freepik.com/free-psd/3d-house-property-illustration_23-2151682346.jpg",
            isLearned = false
        )

        Mockito.`when`(sharedPreferences.getBoolean(word.word, false)).thenReturn(true)

        val isLearned = viewModel.isSavedLearned(word)

        assertEquals(true, isLearned)
    }
}
