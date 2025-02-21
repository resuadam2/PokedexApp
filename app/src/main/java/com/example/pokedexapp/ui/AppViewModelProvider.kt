package com.example.pokedexapp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pokedexapp.PokedexApplication
import com.example.pokedexapp.ui.screens.DetailViewModel
import com.example.pokedexapp.ui.screens.ListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ListViewModel(
                pokedexApplication().appContainer.pokemonRepository
            )
        }
        initializer {
            DetailViewModel(
                this.createSavedStateHandle(),
                pokedexApplication().appContainer.pokemonRepository
            )
        }
    }
}

fun CreationExtras.pokedexApplication(): PokedexApplication =
(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PokedexApplication)