package br.com.aulaandroid.ui.favorite

import br.com.aulaandroid.data.model.GithubUser

sealed class FavoriteState {
    object Default: FavoriteState()
    data class Success(val result: List<GithubUser>): FavoriteState()
    data class Failure(val ex: Exception) : FavoriteState()
}