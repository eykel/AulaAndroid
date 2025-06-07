package br.com.aulaandroid.data.repository

import br.com.aulaandroid.data.model.GithubUser
import br.com.aulaandroid.data.model.GithubUserList
import br.com.aulaandroid.data.model.UserDetailModel
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    suspend fun getUserList(param: String) : RequestHandler<GithubUserList>

    suspend fun getGitHubUser(id: Int) : RequestHandler<Flow<GithubUser>>

    suspend fun getUserDetail(login: String): RequestHandler<UserDetailModel>

    suspend fun getFavoriteList() : RequestHandler<Flow<List<GithubUser>>>

    suspend fun setFavorite(user: GithubUser) : RequestHandler<Long>
}