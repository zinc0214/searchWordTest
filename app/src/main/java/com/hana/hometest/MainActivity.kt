package com.hana.hometest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.hana.hometest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = WordsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViews()
    }

    private fun setUpViews() {
        binding.searchEditTextView.doOnTextChanged { text, start, before, count ->
            val findList = findText(text.toString())
            adapter.updateList(text.toString(), findList)
        }
        adapter.submitList(originWordList)
        binding.recyclerView.adapter = adapter
    }

    private fun findText(findWord: String): List<Word> {
        val list = originWordList.filter {
            it.visibleText.contains(findWord) || it.similarTextList.contains(findWord)
        }
        return list
    }

    private val originWordList = buildList {
        add(
            Word(
                id = 1, visibleText = "테스트1", similarTextList = listOf("테트", "테토")
            )
        )
        add(
            Word(
                id = 2, visibleText = "적금", similarTextList = listOf("예금", "예적금", "통장")
            )
        )
        add(
            Word(
                id = 3, visibleText = "입출금", similarTextList = listOf("요구불", "계좌", "통장")
            )
        )
        add(
            Word(
                id = 4, visibleText = "신분증 인증", similarTextList = listOf("민증", "여권", "지문")
            )
        )
    }
}