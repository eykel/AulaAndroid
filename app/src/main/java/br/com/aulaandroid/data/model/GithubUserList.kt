package br.com.aulaandroid.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserList(
    @SerializedName("items")
    val users: MutableList<GithubUser>
)