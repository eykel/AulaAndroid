package br.com.aulaandroid.data.model

import com.google.gson.annotations.SerializedName


data class UserService(
    @SerializedName("name")
    val name: String,
    @SerializedName("birthDay")
    val birthDay: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String?,
    val id: String?
)