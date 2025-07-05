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
        password = this.password,
        id = this.id
    )

fun UserService.toUserModel() =
    UserModel(
        name = this.name,
        birthDay = this.birthDay,
        email = this.email,
        password = this.password,
        id = this.id.orEmpty()
    )

fun GithubUserModel.toGithubUserResponse(ownerId: String) =
    GithubUserResponse(
        owner = if(ownerId.isNotEmpty()) ownerId else this.owner,
        userName = this.userName,
        avatarImage = this.avatarImage,
        favorite = this.favorite,
        serverId = this.serverId,
    )

fun GithubUserResponse.toGithubUserModel() =
    GithubUserModel(
        owner = this.owner.orEmpty(),
        userName = this.userName,
        avatarImage = this.avatarImage,
        favorite = this.favorite,
        serverId = this.serverId,
    )

fun GithubUserListResponse.toGithubUserListModel(favoriteList: List<GithubUserResponse>): GithubUserListModel {
    val resultList = mutableListOf<GithubUserModel>()
    val favoriteIds = favoriteList.map {  it.serverId }

    this.users.map { usersFromServer ->
        usersFromServer.favorite = favoriteIds.contains(usersFromServer.serverId)
        resultList.add(usersFromServer.toGithubUserModel())
    }

    return GithubUserListModel(resultList)
}

fun List<GithubUserResponse>.toListOfGithubUserModel() =
    this.map {
        it.toGithubUserModel()
    }

