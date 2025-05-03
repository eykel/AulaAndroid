package br.com.aulaandroid.data.repository

import br.com.aulaandroid.util.RequestHandler

interface GithubRepository {

    suspend fun getUserList(param: String) : RequestHandler

    suspend fun getUserDetail(login: String): RequestHandler

    suspend fun getFavoriteList() : RequestHandler
}