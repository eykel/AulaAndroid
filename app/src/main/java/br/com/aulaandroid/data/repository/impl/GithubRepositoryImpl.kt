package br.com.aulaandroid.data.repository.impl

import android.util.Log
import br.com.aulaandroid.data.local.dao.UserDAO
import br.com.aulaandroid.data.model.GithubUser
import br.com.aulaandroid.data.model.GithubUserList
import br.com.aulaandroid.data.model.UserDetailModel
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.Flow

class GithubRepositoryImpl(
    val networking: GithubNetworking,
    val mDatabase: UserDAO
) : GithubRepository {

    override suspend fun getUserList(param: String): RequestHandler<GithubUserList> = networking.gitUserList(param)
    override suspend fun getGitHubUser(id: Int): RequestHandler<Flow<GithubUser>> {
        return try {
            mDatabase.getById(id)
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            Log.e("ERROR", "message: ${ex.message} -- cause ${ex.cause} -- stackTrace: ${ex.stackTrace}")
            RequestHandler.Failure(Exception("Falha ao pegar o usuário no BD"))
        }
    }

    override suspend fun getUserDetail(login: String): RequestHandler<UserDetailModel> = networking.getUserDetail(login)

    override suspend fun getFavoriteList(): RequestHandler<Flow<List<GithubUser>>> {
        return try {
            mDatabase.getFavorites()
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            Log.e("ERROR", "message: ${ex.message} -- cause ${ex.cause} -- stackTrace: ${ex.stackTrace}")
            RequestHandler.Failure(Exception("Falha ao buscar lista de favoritos"))
        }
    }

    override suspend fun setFavorite(user: GithubUser) : RequestHandler<Long> {
        return try {
            mDatabase.insert(user.copy(favorite = !user.favorite))
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            Log.e("ERROR", "message: ${ex.message} -- cause ${ex.cause} -- stackTrace: ${ex.stackTrace}")
            RequestHandler.Failure(Exception("Falha ao salvar favorito"))
        }
    }
}