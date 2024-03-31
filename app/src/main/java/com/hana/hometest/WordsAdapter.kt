package com.hana.hometest

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.TtsSpan.TimeBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hana.hometest.databinding.ItemWordBinding
import java.util.TimeZone

class WordsAdapter() : ListAdapter<Word, WordsAdapter.WordViewHolder>(WordDiff) {

    private var searchedWorld = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WordViewHolder(val binding: ItemWordBinding) : ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.textView.text = word.visibleText

            val colorIndexList = mutableListOf<Int>()
            var findIndex = word.visibleText.indexOf(searchedWorld, 0)

            if (findIndex >= 0) {
                colorIndexList.add(findIndex)
            }
            while (findIndex >= 0 && searchedWorld.isNotEmpty()) {
                findIndex = word.visibleText.indexOf(searchedWorld, findIndex + 1);
                if (findIndex >= 0) {
                    colorIndexList.add(findIndex)
                }
            }

            val spannableString = if (colorIndexList.isEmpty()) {
                SpannableString(word.visibleText).apply {
                    setSpan(
                        ForegroundColorSpan(Color.BLACK),
                        0,
                        word.visibleText.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } else {
                SpannableString(word.visibleText).apply {
                    colorIndexList.forEach {
                        setSpan(
                            ForegroundColorSpan(Color.BLUE),
                            it,
                            it + searchedWorld.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                }
            }

            binding.textView.text = spannableString
        }
    }

    fun updateList(word: String, list: List<Word>) {
        searchedWorld = word
        submitList(list)
    }

    object WordDiff : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.visibleText == newItem.visibleText
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }
}