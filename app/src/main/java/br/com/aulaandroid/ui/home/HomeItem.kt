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
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.data.model.GithubUser
import coil3.compose.AsyncImage

@Composable
fun HomeItem(user: GithubUser){
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarImage,
            contentDescription = "",
            modifier = Modifier.padding(6.dp).clip(CircleShape),
        )

        Text(
            user.userName,
            modifier = Modifier.padding(20.dp)
        )
    }
}