package com.example.pokedexapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokedexapp.ui.navigation.PokedexNavHost

@Composable
fun PokedexApp(navController: NavHostController = rememberNavController()) {
    PokedexNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null, // El ScrollBehavior es un comportamiento que se le puede dar al AppBar
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}