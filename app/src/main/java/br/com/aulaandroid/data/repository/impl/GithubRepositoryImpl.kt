package br.com.aulaandroid.data.repository.impl

import br.com.aulaandroid.data.local.dao.UserDAO
import br.com.aulaandroid.data.local.utils.SessionCache
import br.com.aulaandroid.data.mappers.toGithubUserListModel
import br.com.aulaandroid.data.mappers.toGithubUserResponse
import br.com.aulaandroid.data.mappers.toListOfGithubUserModel
import br.com.aulaandroid.data.model.GithubUserResponse
import br.com.aulaandroid.data.model.UserDetailResponse
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.data.util.Logger
import br.com.aulaandroid.ui.home.model.GithubUserListModel
import br.com.aulaandroid.ui.home.model.GithubUserModel
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GithubRepositoryImpl(
    val networking: GithubNetworking,
    val mDatabase: UserDAO,
    private val sessionCache: SessionCache
) : GithubRepository {

    private val logger = Logger(TAG)

    override suspend fun getUserList(param: String): RequestHandler<GithubUserListModel> {
        when(val serverList = networking.gitUserList(param)){
            is RequestHandler.Failure -> return RequestHandler.Failure(serverList.ex)
            is RequestHandler.Success -> {
                val session = sessionCache.getActiveSession()

                val favoriteList = mDatabase.getStaticFavoriteList(session?.user?.id.orEmpty())

                return RequestHandler.Success(
                    serverList.content.toGithubUserListModel(favoriteList)
                )
            }
        }
    }
    override suspend fun getGitHubUser(id: Int): RequestHandler<Flow<GithubUserResponse>> {
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

    override suspend fun getUserDetail(login: String): RequestHandler<UserDetailResponse> = networking.getUserDetail(login)

    override suspend fun getFavoriteList(): RequestHandler<Flow<List<GithubUserModel>>> {
        return try {
            val session = sessionCache.getActiveSession()
            val flow = mDatabase.getFavorites(session?.user?.id.orEmpty())
                .map { responseList ->
                    responseList.toListOfGithubUserModel()
                }
            RequestHandler.Success(flow)
        }catch (ex: Exception){
            logger.logError(GET_FAVORITE_LIST, ex)
            RequestHandler.Failure(Exception("Falha ao buscar lista de favoritos"))
        }
    }

    override suspend fun setFavorite(user: GithubUserModel) : RequestHandler<Long> {
        return try {
            val session = sessionCache.getActiveSession()
            mDatabase.insert(user.toGithubUserResponse(session?.user?.id.orEmpty()))
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