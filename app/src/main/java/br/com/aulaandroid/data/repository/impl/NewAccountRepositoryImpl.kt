package br.com.aulaandroid.data.repository.impl

import br.com.aulaandroid.data.repository.NewAccountRepository
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.util.RequestHandler

class NewAccountRepositoryImpl(
    private val networking: NewAccountNetworking
) : NewAccountRepository {

    override suspend fun newAccount(user: UserModel) : RequestHandler =
        networking.newAccount(user)

}