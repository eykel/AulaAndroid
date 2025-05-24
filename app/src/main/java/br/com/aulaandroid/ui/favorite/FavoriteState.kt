package br.com.aulaandroid.ui.favorite

import br.com.aulaandroid.data.model.GithubUserList

sealed class FavoriteState {
    object Default: FavoriteState()
    data class Success(val result: GithubUserList): FavoriteState()
    data class Failure(val ex: Exception) : FavoriteState()
}