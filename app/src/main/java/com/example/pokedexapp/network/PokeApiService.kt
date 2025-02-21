package com.example.pokedexapp.network

import com.example.pokedexapp.model.Pokemon
import com.example.pokedexapp.model.PokemonList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonList>

    @GET("pokemon/{name}")
    suspend fun getPokemon(
        @Path("name") id: String
    ): Response<Pokemon>
}