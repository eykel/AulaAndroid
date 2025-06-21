package br.com.aulaandroid.data.repository.impl

import android.util.Log
import br.com.aulaandroid.data.local.dao.UserDAO
import br.com.aulaandroid.data.model.GithubUser
import br.com.aulaandroid.data.model.GithubUserList
import br.com.aulaandroid.data.model.UserDetailModel
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.data.util.Logger
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.Flow

class GithubRepositoryImpl(
    val networking: GithubNetworking,
    val mDatabase: UserDAO
) : GithubRepository {

    private val logger = Logger(TAG)

    override suspend fun getUserList(param: String): RequestHandler<GithubUserList> {
        when(val serverList = networking.gitUserList(param)){
            is RequestHandler.Failure -> return RequestHandler.Failure(serverList.ex)
            is RequestHandler.Success -> {

                val resultList = mutableListOf<GithubUser>()
                val favoriteList = mDatabase.getStaticFavoriteList()

                favoriteList.map { favoriteLocal ->
                    serverList.content.users.map { userFromServer ->
                        if(favoriteLocal.id == userFromServer.id){
                            userFromServer.favorite = true
                        }
                        resultList.add(userFromServer)
                    }
                }

                return RequestHandler.Success(GithubUserList(resultList))
            }
        }
    }
    override suspend fun getGitHubUser(id: Int): RequestHandler<Flow<GithubUser>> {
        return try {
            mDatabase.getById(id)
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            logger.logError(GET_GITHUB_USER, ex)
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
            logger.logError(GET_FAVORITE_LIST, ex)
            RequestHandler.Failure(Exception("Falha ao buscar lista de favoritos"))
        }
    }

    override suspend fun setFavorite(user: GithubUser) : RequestHandler<Long> {
        return try {
            mDatabase.insert(user)
                .run {
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            logger.logError(SET_FAVORITE, ex)
            RequestHandler.Failure(Exception("Falha ao salvar favorito"))
        }
    }

    companion object{
        private const val TAG = "GithubRepositoryImpl"
        private const val GET_GITHUB_USER = "GET_GITHUB_USER"
        private const val GET_FAVORITE_LIST = "GET_FAVORITE_LIST"
        private const val SET_FAVORITE = "SET_FAVORITE"
    }
}