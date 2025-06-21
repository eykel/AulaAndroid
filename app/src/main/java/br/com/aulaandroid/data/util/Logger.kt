package br.com.aulaandroid.data.util

import android.util.Log

class Logger(private val tag: String) {

    fun logSuccess(){
        //TODO
    }
    fun logError(operation: String, exception: Exception){
        Log.e(tag, "$operation ERROR")
        Log.e(tag, "message: ${exception.message}")
        Log.e(tag, "cause ${exception.cause}")
        Log.e(tag, "stackTrace: ${exception.stackTrace}")
    }
}