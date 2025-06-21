package br.com.aulaandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(
    @SerializedName("id")
    val id : Int = 0,
    @SerializedName("name")
    val nickName: String? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("avatar_url")
    val avatar: String? = null,
    @SerializedName("location")
    val location: String? = null,
    @SerializedName("bio")
    val bio: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("url")
    val gitHubUrl: String? = null,
    var favorite: Boolean = false
)