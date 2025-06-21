package br.com.aulaandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "User")
data class GithubUserResponse(
    @PrimaryKey
    val id: Int = 0,
    @SerializedName("login")
    val userName: String,
    @SerializedName("avatar_url")
    val avatarImage: String,
    var favorite: Boolean = false
)