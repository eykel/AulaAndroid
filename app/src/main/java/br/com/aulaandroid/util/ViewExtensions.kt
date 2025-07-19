package br.com.aulaandroid.util

fun String.formatDate() : String{
    val digits = this.filter { it.isDigit() }

    val builder = StringBuilder()
    for(i in digits.indices){
        if(i == 2 || i == 4) builder.append("/")
        if(i < 20) builder.append(digits[i])
    }
    return builder.toString()
}