package br.com.aulaandroid.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    object LoginScreen : Route()
    @Serializable
    object NewAccountScreen : Route()
    @Serializable
    object HomeScreen : Route()
    @Serializable
    object DetailScreen : Route()
}