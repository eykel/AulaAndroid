package br.com.aulaandroid.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.aulaandroid.data.model.UserDetailResponse
import br.com.aulaandroid.data.repository.GithubRepository
import br.com.aulaandroid.util.RequestHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(val repository: GithubRepository) : ViewModel() {

    private val _detailState = MutableStateFlow<DetailState>(DetailState.Default)
    val detailState = _detailState.asStateFlow()

    fun getUserDetail(login: String){
        viewModelScope.launch {
            when (val result = repository.getUserDetail(login)) {
                is RequestHandler.Failure -> _detailState.value = DetailState.Failure

                is RequestHandler.Success -> {
                    _detailState.value = DetailState.Success(result.content)
                }
            }
        }
    }
}