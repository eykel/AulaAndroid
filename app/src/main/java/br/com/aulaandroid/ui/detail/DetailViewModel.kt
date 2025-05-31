package br.com.aulaandroid.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.model.UserDetailModel
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(val repository: GithubRepository) : ViewModel() {

    private val _detailState = MutableStateFlow<DetailState>(DetailState.Default)
    val detailState = _detailState.asStateFlow()

    private val _updatedFavorite = MutableStateFlow<Boolean>(false)
    val updatedFavorite = _updatedFavorite.asStateFlow()

    fun getUserDetail(login: String){
        viewModelScope.launch {
            when (val result = repository.getUserDetail(login)) {
                is RequestHandler.Failure -> {
                    Log.d("PASSEI AQUI", "getUserDetail: ${result.ex}")
                    _detailState.value = DetailState.Failure(result.ex)
                }

                is RequestHandler.Success -> {
                    Log.d("PASSEI AQUI", "getUserDetail: ${result.content}")
                    _detailState.value = DetailState.Success(result.content as UserDetailModel)
                }
            }
        }
    }

    fun favoriteUser(user: UserDetailModel){
        viewModelScope.launch {
            when(repository.setFavorite(user.id, !user.favorite)){
                is RequestHandler.Failure -> {
                    _updatedFavorite.value = false
                }
                is RequestHandler.Success -> {
                    _updatedFavorite.value = true
                }
            }
        }
    }
}