package br.com.aulaandroid.ui.home.model


sealed class HomeState {
    object Default: HomeState()
    data class Success(val result: GithubUserListModel): HomeState()
    data class Failure(val ex: Exception) : HomeState()
}