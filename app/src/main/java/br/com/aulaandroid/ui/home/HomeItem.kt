package br.com.aulaandroid.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.data.model.GithubUser
import br.com.aulaandroid.ui.theme.MyBlue
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun HomeItem(user: GithubUser, onItemClick: (String) -> Unit = {}, onFavoriteClick: (GithubUser) -> Unit = {}){
    var favorite by remember { mutableStateOf(user.favorite) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {
                onItemClick(user.userName)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarImage)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .padding(6.dp)
                .clip(CircleShape),
            fallback = painterResource(id = R.drawable.user_circle),
            error = painterResource(id = R.drawable.user_circle)
        )

        Text(
            user.userName,
            modifier = Modifier.padding(20.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = if(favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            stringResource(R.string.favorite_icon_desc),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(35.dp)
                .clickable {
                    favorite = !favorite
                    user.favorite = !user.favorite
                    onFavoriteClick.invoke(user)
                },
            tint = MyBlue
        )
    }
}