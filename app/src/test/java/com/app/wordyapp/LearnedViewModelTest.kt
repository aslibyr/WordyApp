import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.wordyapp.data.Word
import com.app.wordyapp.ui.learned.LearnedViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LearnedViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var viewModel: LearnedViewModel

    @Before
    fun setup() {
        sharedPreferences = Mockito.mock(SharedPreferences::class.java)
        editor = Mockito.mock(SharedPreferences.Editor::class.java)


        Mockito.`when`(sharedPreferences.edit()).thenReturn(editor)

        Mockito.`when`(editor.putBoolean(Mockito.anyString(), Mockito.anyBoolean()))
            .thenReturn(editor)

        viewModel = LearnedViewModel(sharedPreferences)
    }

    @Test
    fun `updateLearnedWords should mark learned words based on SharedPreferences`() = runTest {
        val words = listOf(
            Word(
                "Dog",
                "Köpek",
                "https://img.freepik.com/premium-photo/3d-dog-frisbee-air-icon-exciting-pet-game-moment-illustration-logo_762678-105051.jpg?ga=GA1.1.1432797158.1700920391&semt=ais_hybrid",
                isLearned = false
            ),
            Word(
                "Cat",
                "Kedi",
                "https://img.freepik.com/premium-photo/3d-cat-with-balloon-icon-feline-fun-with-floating-toy-illustration-logo_762678-103833.jpg?ga=GA1.1.1432797158.1700920391&semt=ais_hybrid",
                isLearned = false
            )
        )

        Mockito.`when`(sharedPreferences.getBoolean("Dog", false)).thenReturn(true)

        viewModel.updateLearnedWords(words)

        val updatedWords = viewModel.words.first()

        assertEquals(1, updatedWords.size)
        assertEquals("Dog", updatedWords[0].word)
        assertEquals(true, updatedWords[0].isLearned)
    }

    @Test
    fun `removeLearnedWord should update word to not learned`() = runTest {
        val word = Word(
            "Cat",
            "Kedi",
            "https://img.freepik.com/premium-photo/3d-cat-with-balloon-icon-feline-fun-with-floating-toy-illustration-logo_762678-103833.jpg?ga=GA1.1.1432797158.1700920391&semt=ais_hybrid",
            isLearned = true
        )

        viewModel.updateLearnedWords(listOf(word))

        viewModel.removeLearnedWord(word)

        val updatedWords = viewModel.words.first()

        assertEquals(0, updatedWords.size)
        Mockito.verify(editor).putBoolean("Cat", false)
        Mockito.verify(editor).apply()
    }
}
