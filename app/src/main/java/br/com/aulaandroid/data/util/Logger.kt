package br.com.aulaandroid.data.util

import android.util.Log

class Logger(private val tag: String) {

    fun logSuccess(operation: String, data: String){
        Log.w(tag, "$operation $SUCCESS")
        Log.w(tag, "$RESULT: $data")
    }
    fun logError(operation: String, exception: Exception){
        Log.e(tag, "$operation $ERROR")
        Log.e(tag, "$MESSAGE: ${exception.message}")
        Log.e(tag, "$CAUSE ${exception.cause}")
        Log.e(tag, "$STACK_TRACE: ${exception.stackTrace}")
    }

    companion object{
        private const val RESULT = "RESULT"
        private const val SUCCESS = "SUCCESS"
        private const val ERROR = "ERROR"
        private const val MESSAGE = "message"
        private const val CAUSE = "cause"
        private const val STACK_TRACE = "stackTrace"
    }
}