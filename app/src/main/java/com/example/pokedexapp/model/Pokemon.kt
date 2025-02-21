package com.example.pokedexapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name: String,
    val sprites: Sprite,
    val types: List<Type>,
) {
    @Serializable
    data class Sprite(
        val front_default: String,
        val front_shiny: String,
    )
    @Serializable
    data class Type(
        val slot: Int,
        val type: TypeInfo,
    ) {
        @Serializable
        data class TypeInfo(
            val name: String,
        )
    }
}