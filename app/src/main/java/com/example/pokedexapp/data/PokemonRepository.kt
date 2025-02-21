package com.example.pokedexapp.data

import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.model.PokemonList
import com.example.pokedexapp.network.PokeApiService

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonList
    suspend fun getPokemonInfo(pokemonName: String): Pokemon
}

class PokemonRepositoryImpl (
    private val pokemonApiService : PokeApiService
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList {
        val response = pokemonApiService.getPokemonList(limit, offset)
        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Pokemon list not found")
        } else {
            throw Exception("Pokemon list not found")
        }
    }
    override suspend fun getPokemonInfo(pokemonName: String): Pokemon {
        val response = pokemonApiService.getPokemon(pokemonName)
        return if (response.isSuccessful) {
            response.body() ?: throw Exception("Pokemon not found")
        } else {
            throw Exception("Pokemon not found")
        }
    }
}