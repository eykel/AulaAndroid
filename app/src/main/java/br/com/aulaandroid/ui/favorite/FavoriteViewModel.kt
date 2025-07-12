package br.com.aulaandroid.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.ui.home.model.GithubUserModel
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(val repository: GithubRepository) : ViewModel() {

    private val _favoriteState = MutableStateFlow<FavoriteState>(FavoriteState.Default)
    val favoriteState = _favoriteState.asStateFlow()

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

    fun updateFavorite(user: GithubUserModel){
        viewModelScope.launch { repository.setFavorite(user) }
    }
}