package br.com.aulaandroid.ui.detail


sealed class DetailState {
    data object Default: DetailState()
    data class Success(val result: String): DetailState()
    data class Failure(val ex: Exception) : DetailState()
}