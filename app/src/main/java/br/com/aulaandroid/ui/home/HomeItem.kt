package br.com.aulaandroid.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.data.model.GithubUser
import coil3.compose.AsyncImage
import coil3.request.ImageRequest

@Composable
fun HomeItem(user: GithubUser){
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarImage)
                .build(),
            contentDescription = "",
            modifier = Modifier.padding(6.dp).clip(CircleShape),
            fallback = painterResource(id = R.drawable.user_circle),
            error = painterResource(id = R.drawable.user_circle)
        )

        Text(
            user.userName,
            modifier = Modifier.padding(20.dp)
        )
    }
}