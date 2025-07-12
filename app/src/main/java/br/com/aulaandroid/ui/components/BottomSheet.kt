package br.com.aulaandroid.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BottomSheetV1(message: String = ""){
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(12.dp) // Outer padding
            .clip(shape = RoundedCornerShape(24.dp))
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(24.dp) // Inner padding
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            imageVector = Icons.Rounded.Info,
            contentDescription = "Alert",
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .size(60.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = message,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(48.dp))

    }
}