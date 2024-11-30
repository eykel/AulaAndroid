package br.com.aulaandroid.data.networking.impl

import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.service.GithubApi
import br.com.aulaandroid.util.RequestHandler

class GithubNetworkingImpl(
    private val githubApi: GithubApi
) : GithubNetworking {
    override suspend fun gitUserList(query: String): RequestHandler {
        return try {
            githubApi.getUserList(query)
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            RequestHandler.Failure(Exception("Falha ao consultar"))
        }
    }
}