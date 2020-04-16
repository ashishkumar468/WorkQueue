# WorkQueue
Service Worker (based on Handler Threads in Android) provides a simple API which lets you perform tasks off the main thread and posts the result back on the main thread
To use the worker, simply create an instance of ServiceWorker
```
var serviceWorker = ServiceWorker("service_worker")
```

And add a task to the ServiceWorker like this
```
serviceWorker.addTask(object : Task<Bitmap>() {
            override fun onExecuteTask(): Bitmap {
                //Work which you want to schedule on the background thread
            }

            override fun onTaskComplete(result: Bitmap) {
                //Use the result on the main thread
            }
        })
```

Note
* Tasks Scheduled on the same instance of WorkerThread will execute sequentially
* Task is generic type so your result can be of any type you speicify as the type parameter.
