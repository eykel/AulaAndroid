package br.com.aulaandroid.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val id: String = "",
    val name: String,
    val birthDay: String,
    val email: String,
    val password: String?
) : Parcelable  {
    companion object {
        fun getUserModel(
            name: String = "",
            birthDay: String = "",
            email: String = "",
            password: String = "",
            id: String = ""
        ) = UserModel(
            name = name,
            birthDay = birthDay,
            email = email,
            password = password,
            id = id
        )
    }
}

