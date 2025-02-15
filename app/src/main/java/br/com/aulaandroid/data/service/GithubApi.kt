package br.com.aulaandroid.data.service

import br.com.aulaandroid.data.model.GithubUserList
import br.com.aulaandroid.data.model.UserDetailModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getUserList(@Query("q") q: String) : GithubUserList

    @GET("users/{login}")
    suspend fun getUserDetail(@Path("login") login: String) : UserDetailModel
}