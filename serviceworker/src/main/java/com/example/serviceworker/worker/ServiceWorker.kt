package com.example.serviceworker.worker

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import java.lang.Exception

class ServiceWorker<T>(name: String) : HandlerThread(name) {
    private lateinit var handler: Handler
    private lateinit var mainThreadHandler: Handler

    init {
        start()
    }

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        handler = Handler(looper)
        mainThreadHandler = Handler(Looper.getMainLooper())
    }

    fun addTask(task: Task<T>) {
        val taskRunnable = Runnable {
            try {
                var result = task.onExecuteTask()
                if (result != null) {
                    mainThreadHandler.post {
                        task.onTaskComplete(result)
                    }
                }
            } catch (exception: Exception) {
                mainThreadHandler.post {
                    task.onError(exception)
                }
            }
        }
        handler.post(taskRunnable)
    }
}