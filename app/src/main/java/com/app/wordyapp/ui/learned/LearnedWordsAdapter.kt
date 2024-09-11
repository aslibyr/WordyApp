package com.app.wordyapp.ui.learned

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.app.wordy.data.Word
import com.app.wordyapp.databinding.WordItemBinding

class LearnedWordsAdapter(val callback: (Word) -> Unit) :
    ListAdapter<Word, LearnedWordsAdapter.WordViewHolder>(LearnedWordDiffCallback()) {

    class WordViewHolder(val binding: WordItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = getItem(position)
        holder.binding.wordText.text = word.word
        holder.binding.wordMeaning.text = word.meaning
        holder.binding.wordImage.load(word.imageUrl)

        holder.itemView.setOnClickListener {
            callback.invoke(word)
        }
    }

    class LearnedWordDiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }
}
