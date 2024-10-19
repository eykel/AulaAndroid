package br.com.aulaandroid.data

import br.com.aulaandroid.util.RequestHandler

interface NewAccountRepository {

    suspend fun newAccount(email: String, password: String) : RequestHandler

    //Vou adicionar de novo um comentário
}