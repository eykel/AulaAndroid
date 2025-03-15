package br.com.aulaandroid.ui.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.navigation.AulaAndroidState
import br.com.aulaandroid.ui.components.ApplyStyle
import br.com.aulaandroid.ui.theme.MyBlue
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun DetailScreen(viewModel: DetailViewModel, login: String, onEvent: (AulaAndroidState) -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.getUserDetail(login)
    }

    when(val detailState =  viewModel.detailState.collectAsState().value) {
        DetailState.Default -> {
            //TODO
        }
        is DetailState.Failure -> {
            //TODO
        }
        is DetailState.Success -> {
            val user = detailState.result
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp, vertical = 20.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.avatar)
                        .build(),
                    contentDescription = stringResource(R.string.user_image_desc),
                    modifier = Modifier
                        .padding(top = 100.dp, bottom = 50.dp)
                        .clip(CircleShape)
                        .border(3.dp, MyBlue, CircleShape),
                    fallback = painterResource(id = R.drawable.user_circle),
                    error = painterResource(id = R.drawable.user_circle),
                )

                user.login?.ApplyStyle(stringResource(R.string.user_login_title))
                user.nickName?.ApplyStyle(stringResource(R.string.user_nick_title))
                user.location?.ApplyStyle(stringResource(R.string.user_location_title))
                user.email?.ApplyStyle(stringResource(R.string.user_email_title))
                user.bio?.let {
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.user_bio_title),
                            modifier = Modifier.padding(vertical = 5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(600)

                        )
                        Text(
                            user.bio,
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                    }
                }



                Row(
                    modifier = Modifier.padding(top = 150.dp)
                ) {
                    Icon(
                        Icons.Outlined.Favorite,
                        stringResource(R.string.favorite_icon_desc),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(35.dp)
                            .clickable { },
                        tint = MyBlue
                    )
                    Icon(
                        Icons.Outlined.Share,
                        stringResource(R.string.share_icon_desc),
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(35.dp)
                            .clickable { },
                        tint = MyBlue
                    )
                }

            }
        }
    }

}