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
    favoriteList.map { favoriteLocal ->
        this.users.map { userFromServer ->
            if(favoriteLocal.id == userFromServer.id){
                userFromServer.favorite = true
            }
            resultList.add(userFromServer.toGithubUserModel())
        }
    }
    return GithubUserListModel(resultList)
}

fun List<GithubUserResponse>.toListOfGithubUserModel() =
    this.map {
        it.toGithubUserModel()
    }

