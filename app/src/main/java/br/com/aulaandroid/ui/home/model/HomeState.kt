package br.com.aulaandroid.ui.home.model

import br.com.aulaandroid.data.model.GithubUserListResponse


sealed class HomeState {
    object Default: HomeState()
    data class Success(val result: GithubUserListModel): HomeState()
    data class Failure(val ex: Exception) : HomeState()
}