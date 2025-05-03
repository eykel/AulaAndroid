package br.com.aulaandroid.data.repository.impl

import br.com.aulaandroid.data.local.dao.UserDAO
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler

class GithubRepositoryImpl(
    val networking: GithubNetworking,
    val mDatabase: UserDAO
) : GithubRepository {

    override suspend fun getUserList(param: String): RequestHandler = networking.gitUserList(param)

    override suspend fun getUserDetail(login: String): RequestHandler = networking.getUserDetail(login)

    override suspend fun getFavoriteList(): RequestHandler {
        return try {
            mDatabase.getFavorites()
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            RequestHandler.Failure(Exception("Falha ao buscar lista de favoritos"))
        }
    }
}