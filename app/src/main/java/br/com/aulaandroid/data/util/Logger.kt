package br.com.aulaandroid.data.util

import android.util.Log

class Logger(private val layerClass: String) {

    fun logSuccess(operation: String, data: String){
        Log.w(TAG, "$LAYER $layerClass")
        Log.w(TAG, "$operation $SUCCESS")
        Log.w(TAG, "$RESULT: $data")
    }
    fun logError(operation: String, exception: Exception){
        Log.w(TAG, "$LAYER $layerClass")
        Log.e(TAG, "$operation $ERROR")
        Log.e(TAG, "$MESSAGE: ${exception.message}")
        Log.e(TAG, "$CAUSE ${exception.cause}")
        Log.e(TAG, "$STACK_TRACE: ${exception.stackTrace}")
    }

    companion object{
        private const val RESULT = "RESULT"
        private const val SUCCESS = "SUCCESS"
        private const val ERROR = "ERROR"
        private const val MESSAGE = "message"
        private const val CAUSE = "cause"
        private const val STACK_TRACE = "stackTrace"
        private const val TAG = "CUSTOM_LOGGER"
        private const val LAYER = "layer"
    }
}