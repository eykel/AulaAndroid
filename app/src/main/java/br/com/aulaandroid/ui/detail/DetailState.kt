package br.com.aulaandroid.ui.detail

import br.com.aulaandroid.data.model.UserDetailModel


sealed class DetailState {
    data object Default: DetailState()
    data class Success(val result: UserDetailModel): DetailState()
    data class Failure(val ex: Exception) : DetailState()
}