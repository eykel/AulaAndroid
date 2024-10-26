package br.com.aulaandroid.ui.newAccount

sealed class NewAccountState {
    object Loading: NewAccountState()
    object Success: NewAccountState()
    data class Failure(val ex: Exception) : NewAccountState()
}