package com.example.vamz

import android.app.DownloadManager.Query
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel: ViewModel(){
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(allPersons)
    val persons = searchText
        .combine(_persons) { text, persons ->
            if(text.isBlank()) {
                persons
            }else{
                persons.filter {
                    it.sediSVyhladavanim(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _persons.value
        )
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

data class Person(
    val firstName: String,
    val lastName: String
) {
    fun sediSVyhladavanim(query: String): Boolean {
        val sediacaKombinacia = listOf(
            "$firstName$lastName",
            "$firstName $lastName",
            "${firstName.first()} ${lastName.first()}"
        )
        return sediacaKombinacia.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

private val allPersons = listOf(
    Person("Marek", "Parek"),
    Person("Karol", "Falol"),
    Person("Denis", "Vahola"),
    Person("Tereziana", "Lietavska"),
    Person("Kajko", "Vajko"),
    Person("Lukas", "Sustr"),
    Person("Filip", "Detolez"),
)