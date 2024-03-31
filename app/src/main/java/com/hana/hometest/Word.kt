package com.hana.hometest

data class SearchData(
    val searchText: String,
    val wordList: List<Word>
)

data class Word(
    val id : Int,
    val visibleText: String,
    val similarTextList: List<String>
)