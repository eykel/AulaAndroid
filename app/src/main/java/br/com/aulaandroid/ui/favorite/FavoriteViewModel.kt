package br.com.aulaandroid.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.model.GithubUser
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.compose
import kotlinx.coroutines.launch

class FavoriteViewModel(val repository: GithubRepository) : ViewModel() {

    private val _favoriteState = MutableStateFlow<FavoriteState>(FavoriteState.Default)
    val favoriteState = _favoriteState.asStateFlow()

    private val _updatedFavorite = MutableStateFlow<Boolean>(false)
    val updatedFavorite = _updatedFavorite.asStateFlow()

    fun getFavoriteList(){
        viewModelScope.launch {
            when(val result = repository.getFavoriteList()){
                is RequestHandler.Failure -> {
                    _favoriteState.value = FavoriteState.Failure(result.ex)
                }
                is RequestHandler.Success -> {
                    result.content.collect { list ->
                        _favoriteState.value = FavoriteState.Success(list)
                    }
                }
            }
        }
    }

    fun updateFavorite(user: GithubUser){
        viewModelScope.launch {
            when(val result = repository.setFavorite(user)){
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