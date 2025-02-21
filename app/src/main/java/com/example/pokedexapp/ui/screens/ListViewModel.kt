package com.example.pokedexapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.data.PokemonRepository
import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.model.PokemonList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class ListUiState(
    val isLoading: Boolean = true,
    val pokemonList: List<PokemonList.PokemonListItem> = emptyList(),
    val favouritePokemonList: List<Pokemon> = emptyList(),
    val error: String = ""
)

class ListViewModel(pokemonRepository: PokemonRepository) : ViewModel() {
    var listUiState = MutableStateFlow(ListUiState())
        private set

    init {
        listUiState.value = listUiState.value.copy(isLoading = true)
        viewModelScope.launch {
            val pokemonListResponse = pokemonRepository.getPokemonList(151, 0)
            listUiState.value = listUiState.value.copy(pokemonList = pokemonListResponse.results, isLoading = false)
        }
    }
}