package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedexapp.PokedexTopAppBar
import com.example.pokedexapp.ui.AppViewModelProvider
import com.example.pokedexapp.ui.navigation.NavigationDestination

object DetailDestination : NavigationDestination {
    override val route = "detail"
    const val pokemonIdArg = "pokemonId"
    val routeWithArgs = "$route/{$pokemonIdArg}"
}

fun typeColor(type: String): Long {
    return when (type) {
        "normal" -> 0xFFA8A77A
        "fire" -> 0xFFEE8130
        "water" -> 0xFF6390F0
        "electric" -> 0xFFF7D02C
        "grass" -> 0xFF7AC74C
        "ice" -> 0xFF96D9D6
        "fighting" -> 0xFFC22E28
        "poison" -> 0xFFA33EA1
        "ground" -> 0xFFE2BF65
        "flying" -> 0xFFA98FF3
        "psychic" -> 0xFFF95587
        "bug" -> 0xFFA6B91A
        "rock" -> 0xFFB6A136
        "ghost" -> 0xFF735797
        "dragon" -> 0xFF6F35FC
        "dark" -> 0xFF705746
        "steel" -> 0xFFB7B7CE
        "fairy" -> 0xFFD685AD
        else -> 0xFF000000
    }
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
                Text(text = detailUiState.value.pokemon.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                },
                    style = MaterialTheme.typography.titleLarge)
                Row {
                    AsyncImage(
                        model = ImageRequest.Builder(
                            context = LocalContext.current
                        ).data(detailUiState.value.pokemon.frontDefault)
                            .build(),
                        contentDescription = "Pokemon image",
                        modifier = Modifier.size(128.dp, 128.dp)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(
                            context = LocalContext.current
                        ).data(detailUiState.value.pokemon.frontShiny)
                            .build(),
                        contentDescription = "Pokemon image",
                        modifier = Modifier.size(128.dp, 128.dp)
                    )
                }
//                Text("image front: " + detailUiState.value.pokemon.frontDefault)
//                Text("image shiny: " + detailUiState.value.pokemon.frontShiny)

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text("Types:   ", style = MaterialTheme.typography.titleMedium)
                    detailUiState.value.pokemon.types.forEach { type ->
                        Card {
                            Text(
                                type,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.background(
                                    color = Color(typeColor(type)),
                                    ).padding(16.dp, 8.dp),
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        }
    }
}