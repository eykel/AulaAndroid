package br.com.aulaandroid.data.mappers

import br.com.aulaandroid.data.model.GithubUserListResponse
import br.com.aulaandroid.data.model.GithubUserResponse
import br.com.aulaandroid.data.model.UserModel
import br.com.aulaandroid.data.model.UserService
import br.com.aulaandroid.ui.home.model.GithubUserListModel
import br.com.aulaandroid.ui.home.model.GithubUserModel
import kotlin.collections.map

fun UserModel.toUserService() =
    UserService(
        name = this.name,
        birthDay = this.birthDay,
        email = this.email,
        password = this.password
    )

fun UserService.toUserModel() =
    UserModel(
        name = this.name,
        birthDay = this.birthDay,
        email = this.email,
        password = this.password
    )

fun GithubUserModel.toGithubUserResponse() =
    GithubUserResponse(
        id = this.id,
        userName = this.userName,
        avatarImage = this.avatarImage,
        favorite = this.favorite
    )

fun GithubUserResponse.toGithubUserModel() =
    GithubUserModel(
        id = this.id,
        userName = this.userName,
        avatarImage = this.avatarImage,
        favorite = this.favorite
    )

fun GithubUserListResponse.toGithubUserListModel(favoriteList: List<GithubUserResponse>): GithubUserListModel {
    val resultList = mutableListOf<GithubUserModel>()
    val favoriteIds = favoriteList.map {  it.id }

    this.users.map { usersFromServer ->
        usersFromServer.favorite = favoriteIds.contains(usersFromServer.id)
        resultList.add(usersFromServer.toGithubUserModel())
    }

    return GithubUserListModel(resultList)
}

fun List<GithubUserResponse>.toListOfGithubUserModel() =
    this.map {
        it.toGithubUserModel()
    }

