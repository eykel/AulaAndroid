package br.com.aulaandroid.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import br.com.aulaandroid.R
import br.com.aulaandroid.ui.components.util.TextFieldType
import br.com.aulaandroid.ui.theme.MyBlue

@Composable
fun TextFieldCustom(
    textValue: String,
    labelText: Int,
    isError: Boolean = false,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit = {},
    type: TextFieldType =  TextFieldType.Default
){

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        modifier = Modifier.padding(20.dp),
        value = textValue,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        label = { Text(text = stringResource(labelText), color = Color.Black) },
        placeholder = { Text(text = stringResource(labelText)) },
        isError = isError,
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Blue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        enabled = enabled,
        visualTransformation =
        if (!passwordVisible && (type == TextFieldType.Password))
            PasswordVisualTransformation()
        else
            VisualTransformation.None
        ,
        trailingIcon = {
            if(type == TextFieldType.Password){
                val image = R.drawable.eye_icon

                val description =
                    if (passwordVisible)
                        stringResource(R.string.hide_password_content_description)
                    else
                        stringResource(
                            R.string.show_password_content_description
                        )

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(image), description, tint = MyBlue)
                }
            }
        }
    )
}