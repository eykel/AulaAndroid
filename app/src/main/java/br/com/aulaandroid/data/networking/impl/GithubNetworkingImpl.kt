package br.com.aulaandroid.data.networking.impl

import android.util.Log
import br.com.aulaandroid.data.model.GithubUserList
import br.com.aulaandroid.data.model.UserDetailModel
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.service.GithubApi
import br.com.aulaandroid.util.RequestHandler

class GithubNetworkingImpl(
    private val githubApi: GithubApi
) : GithubNetworking {
    override suspend fun gitUserList(query: String): RequestHandler<GithubUserList> {
        return try {
            githubApi.getUserList(query)
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            Log.e("ERROR", "message: ${ex.message} -- cause ${ex.cause} -- stackTrace: ${ex.stackTrace}")
            RequestHandler.Failure(Exception("Falha ao consultar"))
        }
    }

    override suspend fun getUserDetail(login: String): RequestHandler<UserDetailModel> {
        return try {
            githubApi.getUserDetail(login)
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            Log.e("ERROR", "message: ${ex.message} -- cause ${ex.cause} -- stackTrace: ${ex.stackTrace}")
            RequestHandler.Failure(Exception("Falha ao buscar detalhes de usuário"))
        }
    }
}