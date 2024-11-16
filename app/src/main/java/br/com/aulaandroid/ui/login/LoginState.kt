package br.com.aulaandroid.ui.login


sealed class LoginState {
    object Default: LoginState()
    object Success: LoginState()
    data class Failure(val ex: Exception) : LoginState()
}