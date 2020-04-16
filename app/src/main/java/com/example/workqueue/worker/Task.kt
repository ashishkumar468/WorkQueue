package com.example.workqueue.worker

import android.util.Log
import java.lang.Exception

abstract class Task<T> {
    val TAG = "##TASK"
    val id = System.currentTimeMillis()
    abstract fun onExecuteTask(): T
    abstract fun onTaskComplete(result: T)
    fun onError(exception: Exception) {
        Log.e(TAG, exception.message)
    }
}