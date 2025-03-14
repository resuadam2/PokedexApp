package com.example.pokedexapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokedexapp.PokedexTopAppBar
import com.example.pokedexapp.model.PokemonList
import com.example.pokedexapp.ui.navigation.NavigationDestination
import com.example.pokedexapp.ui.AppViewModelProvider
import java.util.Locale

object ListDestination : NavigationDestination {
    override val route = "list"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onPokemonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val listUiState by viewModel.listUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PokedexTopAppBar(
                title = "Pokedex",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
            )
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (listUiState.isLoading) {
                Text("Loading...")
            } else {
                LazyColumn {
                    items(
                        items = listUiState.pokemonList,
                        key = { pokemon -> pokemon.name })
                    { pokemon ->
                        PokemonCard(
                            pokemon = pokemon,
                            onPokemonClick = { onPokemonClick(pokemon.name) },
                            isFavorite = pokemon.name in listUiState.favouritePokemonList.map { it.name },
                            onPokemonFavorite = { }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: PokemonList.PokemonListItem,
    isFavorite: Boolean = false,
    onPokemonClick: () -> Unit,
    onPokemonFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    ) {

        Row (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                fontStyle = MaterialTheme.typography.titleMedium.fontStyle
            )
            Row {
                IconButton(
                    onClick = { onPokemonFavorite() }
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite"
                    )
                }
                IconButton(
                    onClick = { onPokemonClick() }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Info"
                    )
                }
            }
        }

    }
}
