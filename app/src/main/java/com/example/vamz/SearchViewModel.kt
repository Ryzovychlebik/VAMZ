package com.example.vamz

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SearchViewModel : ViewModel() {
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _materialy = MutableStateFlow(allMaterials)
    val materialy = searchText
        .combine(_materialy) { text, materials ->
            if (text.isBlank()) {
                materials
            } else {
                materials.filter { it.sediSVyhladavanim(text) }
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
    val imageRes: Int,
    val lokacia: String
) {
    fun sediSVyhladavanim(query: String): Boolean {
        val searchTerms = listOf(
            nazovMaterialu,
            nazovMaterialu.first().toString()
        )
        return searchTerms.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

// Assuming you have drawable resources with these names
private val allMaterials = listOf(
    Material("White Iron Chunk", R.drawable.mapchenyun, "mapchenyun"),
    Material("White Iron Chunk", R.drawable.mapchenyun, "mapchenyun"),
    Material("White Iron Chunk", R.drawable.mapchenyun, "mapchenyun"),
    Material("Crystal Chunk", R.drawable.mapchenyun, "mapchenyun"),
    Material("Magical Crystal Chunk", R.drawable.mapchenyun, "mapchenyun"),
    Material("Iron Chunk", R.drawable.mapchenyun, "mapchenyun"),
    Material("Clearwater Jade", R.drawable.mapchenyun, "mapchenyun"),
    Material("Qingxin", R.drawable.mapchenyun, "mapchenyun"),
    Material("Violetgrass", R.drawable.mapchenyun, "mapchenyun")
)
