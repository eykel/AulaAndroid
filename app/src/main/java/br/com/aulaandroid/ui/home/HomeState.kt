package br.com.aulaandroid.ui.home

import br.com.aulaandroid.data.model.GithubUserList


sealed class HomeState {
    object Default: HomeState()
    data class Success(val result: GithubUserList): HomeState()
    data class Failure(val ex: Exception) : HomeState()
}