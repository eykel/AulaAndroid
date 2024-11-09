package br.com.aulaandroid.navigation

sealed class AulaAndroidState {
    data class Navigate(val route:Route): AulaAndroidState()
    data class Error(val message: String) : AulaAndroidState()
}
