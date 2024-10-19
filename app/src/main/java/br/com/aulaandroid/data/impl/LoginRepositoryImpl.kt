package br.com.aulaandroid.data.impl

import br.com.aulaandroid.data.LoginRepository
import br.com.aulaandroid.data.networking.LoginNetworking

class LoginRepositoryImpl(private val networking: LoginNetworking) : LoginRepository {
    override suspend fun login(): String {
        TODO("Not yet implemented")
    }
}