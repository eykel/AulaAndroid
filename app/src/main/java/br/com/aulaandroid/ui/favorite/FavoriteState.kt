package br.com.aulaandroid.ui.favorite

import br.com.aulaandroid.ui.home.model.GithubUserModel

sealed class FavoriteState {
    object Default: FavoriteState()
    data class Success(val result: List<GithubUserModel>): FavoriteState()
    data class Failure(val ex: Exception) : FavoriteState()
}