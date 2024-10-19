package br.com.aulaandroid.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val name: String,
    val birthDate: String,
    val email: String,
    val password: String
) : Parcelable

