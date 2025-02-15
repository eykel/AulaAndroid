package br.com.aulaandroid.data.networking

import br.com.aulaandroid.util.RequestHandler

interface GithubNetworking {

    suspend fun gitUserList(query: String) : RequestHandler

    suspend fun getUserDetail(login: String): RequestHandler
}