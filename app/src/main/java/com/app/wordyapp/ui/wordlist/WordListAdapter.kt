package com.app.wordyapp.ui.wordlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.load
import com.app.wordy.data.Word
import com.app.wordyapp.R
import com.app.wordyapp.databinding.WordItemBinding

class WordListAdapter(val callback: (Word) -> Unit, private val context: Context) :
    ListAdapter<Word, WordListAdapter.WordViewHolder>(
        WordDiffCallback()
    ) {

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

        if (word.isLearned) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
        }

        holder.itemView.setOnClickListener {
            if (word.isLearned.not()) {
                callback.invoke(word)
            }
        }
    }


    class WordDiffCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }
}