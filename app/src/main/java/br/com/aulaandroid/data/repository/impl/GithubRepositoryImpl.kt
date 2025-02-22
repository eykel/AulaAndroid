package br.com.aulaandroid.data.repository.impl

import br.com.aulaandroid.data.networking.GithubNetworking
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler

class GithubRepositoryImpl(val networking: GithubNetworking) : GithubRepository {

    override suspend fun getUserList(param: String): RequestHandler = networking.gitUserList(param)

    override suspend fun getUserDetail(login: String): RequestHandler = networking.getUserDetail(login)
}