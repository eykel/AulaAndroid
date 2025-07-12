package br.com.aulaandroid.data.repository.impl

import android.content.Context
import br.com.aulaandroid.R
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
    private val sessionCache: SessionCache,
    private val context: Context
) : GithubRepository {

    private val logger = Logger(TAG)

    override suspend fun getUserList(param: String): RequestHandler<GithubUserListModel> {
        when(val serverList = networking.gitUserList(param)){
            is RequestHandler.Failure -> {
                logger.logError(GET_USER_LIST, serverList.ex)
                return RequestHandler.Failure(Exception(context.getString(R.string.failed_to_consult_user)))
            }
            is RequestHandler.Success -> {
                logger.logSuccess(GET_USER_LIST, serverList.content.toString())
                val session = sessionCache.getActiveSession()

                val favoriteList = mDatabase.getStaticFavoriteList(session?.user?.id.orEmpty())

                return RequestHandler.Success(
                    serverList.content.toGithubUserListModel(favoriteList)
                )
            }
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
            logger.logSuccess(GET_FAVORITE_LIST, flow.toString())
            RequestHandler.Success(flow)
        }catch (ex: Exception){
            logger.logError(GET_FAVORITE_LIST, ex)
            RequestHandler.Failure(Exception(context.getString(R.string.failed_to_search_favorite_list)))
        }
    }

    override suspend fun setFavorite(user: GithubUserModel) : RequestHandler<Long> {
        return try {
            val session = sessionCache.getActiveSession()
            mDatabase.insert(user.toGithubUserResponse(session?.user?.id.orEmpty()))
                .run {
                    logger.logSuccess(SET_FAVORITE, this.toString())
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            logger.logError(SET_FAVORITE, ex)
            RequestHandler.Failure(Exception(context.getString(R.string.failed_to_save_favorite)))
        }
    }

    companion object{
        private const val TAG = "GithubRepositoryImpl"
        private const val GET_FAVORITE_LIST = "GET_FAVORITE_LIST"
        private const val SET_FAVORITE = "SET_FAVORITE"
        private const val GET_USER_LIST = "GET_USER_LIST"
    }
}