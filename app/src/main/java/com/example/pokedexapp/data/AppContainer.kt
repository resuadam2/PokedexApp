package com.example.pokedexapp.data

import com.example.pokedexapp.network.PokeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface AppContainer {
    val pokemonRepository: PokemonRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://pokeapi.co/api/v2/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: PokeApiService by lazy { retrofit.create(PokeApiService::class.java) }

    override val pokemonRepository: PokemonRepository by lazy {
        PokemonRepositoryImpl(retrofitService)
    }

}