package br.com.aulaandroid.ui.home.model


data class GithubUserModel(
    var owner: String = "",
    val userName: String,
    val avatarImage: String,
    var favorite: Boolean = false,
    val serverId: Int = 0 ,
)