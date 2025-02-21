package com.example.pokedexapp.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonList(
    val results: List<PokemonListItem>
) {
    @Serializable
    data class PokemonListItem(
        val name: String,
        val url: String
    )
}