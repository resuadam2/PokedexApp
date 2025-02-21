package com.example.pokedexapp.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.pokedexapp.ui.screens.DetailDestination
import com.example.pokedexapp.ui.screens.DetailScreen
import com.example.pokedexapp.ui.screens.ListDestination
import com.example.pokedexapp.ui.screens.ListScreen

@Composable
fun PokedexNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ListDestination.route,
        modifier = modifier
    ) {
        composable(route = ListDestination.route) {
            ListScreen(
                onPokemonClick = {
                    navController.navigate("${DetailDestination.route}/$it")
                },
            )
        }
        composable(route = DetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(DetailDestination.pokemonIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(
                navigateBack = { navController.navigateUp() },
            )
        }
    }
}