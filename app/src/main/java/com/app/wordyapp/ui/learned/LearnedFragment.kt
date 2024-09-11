package com.app.wordyapp.ui.learned

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.wordyapp.data.Word
import com.app.wordyapp.databinding.FragmentLearnedBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.InputStreamReader

@AndroidEntryPoint
class LearnedFragment : Fragment() {
    private var _binding: FragmentLearnedBinding? = null
    private val binding get() = _binding!!

    private lateinit var learnedAdapter: LearnedWordsAdapter
    private val viewModel by viewModels<LearnedViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLearnedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCollectors()
        setListeners()
        val words = loadWordsFromJson()
        viewModel.updateLearnedWords(words)
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                binding.resetButton.visibility =
                    if (firstVisibleItemPosition > 2) View.VISIBLE else View.GONE
            }
        })
    }

    private fun initAdapter() {
        learnedAdapter = LearnedWordsAdapter(callback = { word: Word ->
            showWordPopup(word = word)
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = learnedAdapter
        }
    }

    private fun initCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.words.collectLatest { words ->
                val wordsList = words.map {
                    it.copy(isLearned = viewModel.isSavedLearned(it))
                }.filter { it.isLearned }
                learnedAdapter.submitList(wordsList) {
                    binding.recyclerView.scrollToPosition(0)
                }
            }
        }
    }

    private fun showWordPopup(word: Word) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Remove Word")
            .setMessage("Do you want to remove '${word.word}' from learned?")
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel.removeLearnedWord(word)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun setListeners() {
        binding.resetButton.setOnClickListener() {
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun loadWordsFromJson(): List<Word> {
        val inputStream = requireActivity().assets.open("words.json")
        val reader = InputStreamReader(inputStream)
        val wordType = object : TypeToken<List<Word>>() {}.type
        return Gson().fromJson(reader, wordType)
    }
}