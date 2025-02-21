package com.example.pokedexapp.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexapp.data.PokemonRepository
import com.example.pokedexapp.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

data class PokemonDetails(
    val name: String,
    val frontDefault: String?,
    val frontShiny: String?,
    val types: List<String>
)

fun Pokemon.toPokemonDetails(): PokemonDetails {
    return PokemonDetails(
        name = name,
        frontDefault = sprites.front_default,
        frontShiny = sprites.front_shiny,
        types = types.map { it.type.name }
    )
}

data class DetailUiState(
    val isLoading: Boolean = true,
    val pokemon: PokemonDetails = PokemonDetails("", "", "", emptyList()),
    val error: String = ""
)


class DetailViewModel (
    savedStateHandle: SavedStateHandle,
    pokemonRepository: PokemonRepository
) : ViewModel() {

    private val pokemonId: String = checkNotNull(savedStateHandle[DetailDestination.pokemonIdArg])

    var detailUiState = MutableStateFlow(DetailUiState())
        private set

    init {
        detailUiState.value = detailUiState.value.copy(isLoading = true)
        viewModelScope.launch {
            detailUiState.value = try {
                val pokemon = pokemonRepository.getPokemonInfo(pokemonId).toPokemonDetails()
                detailUiState.value.copy(pokemon = pokemon, isLoading = false)
            } catch (e: Exception) {
                detailUiState.value.copy(error = e.message ?: "An error occurred", isLoading = false)
            }
        }
    }
}