package com.example.torushoppingapp.Helper

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class CustomNotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun doWork(): Result {
        val channelId = inputData.getString("channelId") ?: "default_channel"
        val title = inputData.getString("title") ?: "Notification"
        val message = inputData.getString("message") ?: "This is a notification."
        val notificationId = inputData.getInt("notificationId", 0)
        val smallIcon = inputData.getInt("smallIcon", android.R.drawable.ic_dialog_info)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)
        return Result.success()
    }
}

