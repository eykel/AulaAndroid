package br.com.aulaandroid.data.model

import com.google.gson.annotations.SerializedName

data class UserDetailModel(
    @SerializedName("id")
    val id : Int = 0,
    @SerializedName("name")
    val nickName: String = "",
    @SerializedName("login")
    var login: String = "",
    @SerializedName("avatar_url")
    var avatar: String = "",
    @SerializedName("location")
    var location: String = "",
    @SerializedName("bio")
    var bio: String = "",
    @SerializedName("email")
    var email: String = ""
)