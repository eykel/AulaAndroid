package br.com.aulaandroid.data.networking

import br.com.aulaandroid.data.model.GithubUserListResponse
import br.com.aulaandroid.data.model.UserDetailResponse
import br.com.aulaandroid.util.RequestHandler

interface GithubNetworking {

    suspend fun gitUserList(query: String) : RequestHandler<GithubUserListResponse>

    suspend fun getUserDetail(login: String): RequestHandler<UserDetailResponse>
}