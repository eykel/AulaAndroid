package br.com.aulaandroid.ui.favorite

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoriteViewModel() : ViewModel() {

    private val _favoriteState = MutableStateFlow<FavoriteState>(FavoriteState.Default)
    val favoriteState = _favoriteState.asStateFlow()

    fun getFavoriteList(){

    }


}