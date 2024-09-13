package com.app.wordyapp.ui.wordlist

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
import com.app.wordyapp.databinding.FragmentHomeBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.InputStreamReader

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordListAdapter: WordListAdapter
    private val viewModel by viewModels<HomeViewModel>()
    var isShuffling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCollectors()
        setListeners()

        val words = loadWordsFromJson()
        viewModel.updateLearnedWords(words)

        binding.swipeRefreshLayout.setOnRefreshListener {
            isShuffling = true
            viewModel.shuffleWords()
            binding.swipeRefreshLayout.isRefreshing = false
        }
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

    private fun initCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.words.collectLatest { words ->
                val wordsList = words.map {
                    it.copy(isLearned = viewModel.isSavedLearned(it))
                }.filter { it.isLearned.not() }
                wordListAdapter.submitList(wordsList) {
                    if (isShuffling) {
                        binding.recyclerView.scrollToPosition(0)
                        isShuffling = false
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        wordListAdapter = WordListAdapter(context = requireContext(), callback = { word: Word ->
            showWordPopup(word = word)
        })
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wordListAdapter
        }
    }

    private fun showWordPopup(word: Word) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Learn Word")
            .setMessage("Do you want to mark '${word.word}' as learned?")
            .setPositiveButton("Yes") { dialog, _ ->
                viewModel.saveLearnedWord(word)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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