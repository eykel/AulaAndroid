package br.com.aulaandroid.data.impl

import br.com.aulaandroid.data.NewAccountRepository
import br.com.aulaandroid.data.networking.NewAccountNetworking
import br.com.aulaandroid.util.RequestHandler

class NewAccountRepositoryImpl(
    private val networking: NewAccountNetworking
) : NewAccountRepository{
    override suspend fun newAccount(email: String, password:String) : RequestHandler =
        networking.newAccount(email, password)


}