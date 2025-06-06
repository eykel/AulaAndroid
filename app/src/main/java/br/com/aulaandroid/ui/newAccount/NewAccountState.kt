package br.com.aulaandroid.ui.newAccount

sealed class NewAccountState {
    object Default: NewAccountState()
    object Success: NewAccountState()
    data class Failure(val ex: Exception) : NewAccountState()
}