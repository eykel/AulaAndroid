package br.com.aulaandroid.data.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatarImage: String
)