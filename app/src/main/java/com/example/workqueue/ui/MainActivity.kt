package com.example.workqueue.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.workqueue.R
import com.example.workqueue.worker.ServiceWorker
import com.example.workqueue.worker.Task
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    lateinit var serviceWorker1: ServiceWorker<Bitmap>
    lateinit var serviceWorker2: ServiceWorker<Bitmap>
    var okHttpClient = OkHttpClient()
    val images = arrayOf("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2e/Lama_glama_Laguna_Colorada_2.jpg/1000px-Lama_glama_Laguna_Colorada_2.jpg", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Pluto-01_Stern_03_Pluto_Color_TXT.jpg/1200px-Pluto-01_Stern_03_Pluto_Color_TXT.jpg", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Perereca-macaco_-_Phyllomedusa_rohdei.jpg/1599px-Perereca-macaco_-_Phyllomedusa_rohdei.jpg", "https://upload.wikimedia.org/wikipedia/commons/c/cb/Broadway_tower_edit.jpg", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Pair_of_Merops_apiaster_feeding.jpg/1600px-Pair_of_Merops_apiaster_feeding.jpg")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serviceWorker1 = ServiceWorker("service_worker_1")
        serviceWorker2 = ServiceWorker("service_worker_2")
        firstButton.setOnClickListener {
            fetchImageOneAndSet()
        }

        secondButton.setOnClickListener {
            fetchImageTwoAndSet()
        }
    }

    private fun fetchImageOneAndSet() {
        serviceWorker1.addTask(object : Task<Bitmap>() {
            override fun onExecuteTask(): Bitmap {
                val request: Request = Request.Builder().url(images[Random.nextInt(0, images.size - 1)]).build()
                val response: Response = okHttpClient.newCall(request).execute()
                return BitmapFactory.decodeStream(response.body?.byteStream())
            }

            override fun onTaskComplete(result: Bitmap) {
                firstIv.setImageBitmap(result)
            }

        })
    }

    private fun fetchImageTwoAndSet() {
        serviceWorker2.addTask(object : Task<Bitmap>() {
            override fun onExecuteTask(): Bitmap {
                val request: Request = Request.Builder().url(images[Random.nextInt(0, images.size - 1)]).build()
                val response: Response = okHttpClient.newCall(request).execute()
                return BitmapFactory.decodeStream(response.body?.byteStream())
            }

            override fun onTaskComplete(result: Bitmap) {
                secondIv.setImageBitmap(result)
            }
        })
    }
}