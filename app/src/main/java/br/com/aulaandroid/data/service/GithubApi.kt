package br.com.aulaandroid.data.service

import br.com.aulaandroid.data.model.GithubUserListResponse
import br.com.aulaandroid.data.model.UserDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getUserList(@Query("q") q: String) : GithubUserListResponse

    @GET("users/{login}")
    suspend fun getUserDetail(@Path("login") login: String) : UserDetailResponse
}