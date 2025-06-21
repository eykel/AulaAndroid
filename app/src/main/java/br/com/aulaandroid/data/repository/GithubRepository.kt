package br.com.aulaandroid.data.repository

import br.com.aulaandroid.data.model.GithubUserResponse
import br.com.aulaandroid.data.model.GithubUserListResponse
import br.com.aulaandroid.data.model.UserDetailResponse
import br.com.aulaandroid.ui.home.model.GithubUserListModel
import br.com.aulaandroid.ui.home.model.GithubUserModel
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.Flow

interface GithubRepository {

    suspend fun getUserList(param: String) : RequestHandler<GithubUserListModel>

    suspend fun getGitHubUser(id: Int) : RequestHandler<Flow<GithubUserResponse>>

    suspend fun getUserDetail(login: String): RequestHandler<UserDetailResponse>

    suspend fun getFavoriteList() : RequestHandler<Flow<List<GithubUserModel>>>

    suspend fun setFavorite(user: GithubUserModel) : RequestHandler<Long>
}