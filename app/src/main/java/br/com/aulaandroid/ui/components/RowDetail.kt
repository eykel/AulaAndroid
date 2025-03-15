package br.com.aulaandroid.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun String.ApplyStyle(title: String) {
    Row(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            title,
            modifier = Modifier.padding(end = 5.dp),
            textAlign = TextAlign.End,
            fontWeight = FontWeight(600)

        )
        Text(
            this@ApplyStyle,
            modifier = Modifier,
            textAlign = TextAlign.Start
        )
    }
}