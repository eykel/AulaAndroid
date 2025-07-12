package br.com.aulaandroid.data.networking.impl

import android.content.Context
import br.com.aulaandroid.R
import br.com.aulaandroid.data.model.GithubUserListResponse
import br.com.aulaandroid.data.model.UserDetailResponse
import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.service.GithubApi
import br.com.aulaandroid.data.util.Logger
import br.com.aulaandroid.util.RequestHandler

class GithubNetworkingImpl(
    private val githubApi: GithubApi,
    private val context: Context
) : GithubNetworking {

    private val logger = Logger(TAG)

    override suspend fun gitUserList(query: String): RequestHandler<GithubUserListResponse> {
        return try {
            githubApi.getUserList(query)
                .run {
                    logger.logSuccess(GET_USER_LIST, this.toString())
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            logger.logError(GET_USER_LIST, ex)
            RequestHandler.Failure(Exception(context.getString(R.string.failed_to_consult_user)))
        }
    }

    override suspend fun getUserDetail(login: String): RequestHandler<UserDetailResponse> {
        return try {
            githubApi.getUserDetail(login)
                .run {
                    logger.logSuccess(GET_USER_DETAIL, this.toString())
                    RequestHandler.Success(this)
                }
        }catch (ex: Exception){
            logger.logError(GET_USER_DETAIL, ex)
            RequestHandler.Failure(Exception(context.getString(R.string.failed_seach_user_detail)))
        }
    }

    companion object{
        private const val TAG = "GithubNetworkingImpl"
        private const val GET_USER_LIST = "GET_USER_LIST"
        private const val GET_USER_DETAIL = "GET_USER_DETAIL"
    }
}