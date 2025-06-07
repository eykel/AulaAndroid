package br.com.aulaandroid.data.networking

import br.com.aulaandroid.data.model.GithubUserList
import br.com.aulaandroid.data.model.UserDetailModel
import br.com.aulaandroid.util.RequestHandler

interface GithubNetworking {

    suspend fun gitUserList(query: String) : RequestHandler<GithubUserList>

    suspend fun getUserDetail(login: String): RequestHandler<UserDetailModel>
}