package br.com.aulaandroid.ui.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.navigation.AulaAndroidState
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun DetailScreen(viewModel: DetailViewModel, login: String, onEvent: (AulaAndroidState) -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.getUserDetail(login)
    }

    val detailState =  viewModel.detailState.collectAsState().value


    when(detailState) {
        DetailState.Default -> {
            //TODO
        }
        is DetailState.Failure -> {
            //TODO
        }
        is DetailState.Success -> {
            val user = detailState.result
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.avatar)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .padding(6.dp)
                    .clip(CircleShape),
                fallback = painterResource(id = R.drawable.user_circle),
                error = painterResource(id = R.drawable.user_circle),
            )

            Text(
                user.login,
                modifier = Modifier.padding(20.dp)
            )
        }
    }

}