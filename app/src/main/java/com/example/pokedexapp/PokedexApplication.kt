package com.example.pokedexapp

import android.app.Application
import com.example.pokedexapp.data.AppContainer
import com.example.pokedexapp.data.DefaultAppContainer

class PokedexApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }
}