package br.com.aulaandroid.ui.components.util

sealed class TextFieldType {
    data object Default: TextFieldType()
    data object Password: TextFieldType()
    data object Search: TextFieldType()
}