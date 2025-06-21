package br.com.aulaandroid.ui.home.model


data class GithubUserModel(
    val id: Int = 0,
    val userName: String,
    val avatarImage: String,
    var favorite: Boolean = false
)