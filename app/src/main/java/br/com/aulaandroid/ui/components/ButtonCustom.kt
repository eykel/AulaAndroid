package br.com.aulaandroid.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.ui.theme.MyBlue

@Composable
fun ButtonCustom(
    onClick: () -> Unit = {},
    loading: Boolean = false,
    text: Int
){
    Button(
        modifier = Modifier
            .padding(10.dp)
            .width(140.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MyBlue),
        onClick = {
            onClick.invoke()
        },
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(ButtonDefaults.IconSize),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = stringResource(text),
            )
        }
    }
}

