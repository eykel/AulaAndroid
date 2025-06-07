package br.com.aulaandroid.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.navigation.AulaAndroidState
import br.com.aulaandroid.navigation.Route
import br.com.aulaandroid.ui.components.GenericError
import br.com.aulaandroid.ui.home.HomeItem
import br.com.aulaandroid.ui.theme.MyBlue

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel,
    onEvent: (AulaAndroidState) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getFavoriteList()
    }

    Content(viewModel, onEvent)
}

@Composable
fun Content(viewModel: FavoriteViewModel,onEvent: (AulaAndroidState) -> Unit){

    val favoriteState = viewModel.favoriteState.collectAsState().value

    when(favoriteState){
        FavoriteState.Default -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(R.string.not_favorite_yet),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(20.dp)
                )
                Icon(
                    Icons.Outlined.Info,
                    "",
                    tint = MyBlue,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        is FavoriteState.Failure -> {
            GenericError()
            onEvent.invoke(AulaAndroidState.Error(favoriteState.ex.message.orEmpty()))
        }
        is FavoriteState.Success -> {
            LazyColumn {
                items(favoriteState.result) {
                    HomeItem(
                        it,
                        { onEvent.invoke(AulaAndroidState.Navigate(Route.DetailScreen(it))) },
                        { viewModel.updateFavorite(it) }
                    )
                }
            }
        }
    }
}