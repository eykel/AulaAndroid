package br.com.aulaandroid.data.model

import android.os.Parcelable
import br.com.aulaandroid.ui.aulas.aula2.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val birthDay: String,
    val email: String,
    val password: String?
) : Parcelable {
    companion object {
        fun getUserModel(
            name: String = "",
            birthDay: String = "",
            email: String = "",
            password: String = ""
        ) = UserModel(
            name = name,
            birthDay = birthDay,
            email = email,
            password = password
        )
    }
}

