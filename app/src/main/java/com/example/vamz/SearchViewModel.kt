package com.example.vamz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _materialy = MutableStateFlow(allMaterials)
    val materialy = searchText
        .combine(_materialy) { text, persons ->
            if(text.isBlank()) {
                persons.filter {
                    it.sediSVyhladavanim("-")
                }
            }else{
                persons.filter {
                    it.sediSVyhladavanim(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _materialy.value
        )
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}

data class Material(
    val nazovMaterialu: String,
    //val lastName: String,
    val lokacia: Int

) {
    fun sediSVyhladavanim(query: String): Boolean {
        val sediacaKombinacia = listOf(
            "$nazovMaterialu",
            "${nazovMaterialu.first()}"
        )
        return sediacaKombinacia.any {
            it.contains(query, ignoreCase = true)
        }
    }

}

private val allMaterials = listOf(
    Material("White Iron Chunk", R.drawable.mapchenyun),
    Material("White Iron Chunk", R.drawable.mapchenyun),
    Material("Crystal Chunk", R.drawable.mapchenyun),
    Material("Magical Crystal Chunk", R.drawable.mapchenyun),
    Material("Iron Chunk", R.drawable.mapchenyun),
    Material("Clearwater Jade", R.drawable.mapchenyun),
    Material("Qingxin", R.drawable.mapchenyun),
    Material("Violetgrass", R.drawable.mapchenyun),
)