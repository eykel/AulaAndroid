package br.com.aulaandroid.ui.detail

import br.com.aulaandroid.data.model.UserDetailResponse


sealed class DetailState {
    data object Default: DetailState()
    data class Success(val result: UserDetailResponse): DetailState()
    data class Failure(val ex: Exception) : DetailState()
}