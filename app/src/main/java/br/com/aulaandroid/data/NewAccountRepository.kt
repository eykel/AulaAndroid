package br.com.aulaandroid.data

import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.util.RequestHandler

interface NewAccountRepository {

    suspend fun newAccount(user: UserModel) : RequestHandler
}