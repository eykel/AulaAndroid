package br.com.aulaandroid.data.model

import com.google.gson.annotations.SerializedName

data class GithubUserListResponse(
    @SerializedName("items")
    val users: MutableList<GithubUserResponse>
)