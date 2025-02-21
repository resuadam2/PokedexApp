package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pokedexapp.PokedexTopAppBar
import com.example.pokedexapp.ui.AppViewModelProvider
import com.example.pokedexapp.ui.navigation.NavigationDestination

object DetailDestination : NavigationDestination {
    override val route = "detail"
    const val pokemonIdArg = "pokemonId"
    val routeWithArgs = "$route/{$pokemonIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val detailUiState = viewModel.detailUiState.collectAsState()
    Scaffold (
        topBar = {
            PokedexTopAppBar(
                title = "Pokemon Details",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) {
        Column (
            modifier = modifier.padding(it).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (detailUiState.value.isLoading) {
                Text("Loading...")
            } else if (detailUiState.value.error.isNotEmpty()) {
                Text("Error: ${detailUiState.value.error}")
            } else {
                Text(text = detailUiState.value.pokemon.name)
                Text("image front: " + detailUiState.value.pokemon.frontDefault)
                Text("image shiny: " + detailUiState.value.pokemon.frontShiny)
                Row {
                    Text("Types: ")
                    detailUiState.value.pokemon.types.forEach { type ->
                        Text(type)
                    }
                }
            }
        }
    }
}