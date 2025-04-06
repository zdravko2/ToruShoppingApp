package com.example.torushoppingapp.Helper

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class CustomNotificationManager(private val context: Context) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun initCustomChannels() {
        registerChannel(
            channelId = "default_channel",
            channelName = "Default Notifications",
            description = "Default notifications for app",
            importance = NotificationManager.IMPORTANCE_DEFAULT
        )

        registerChannel(
            channelId = "order_channel",
            channelName = "Order Notifications",
            description = "Notifications for order updates",
            importance = NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun registerChannel(
        channelId: String,
        channelName: String,
        description: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                this.description = description
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun showNotification(
        channelId: String,
        notificationId: Int,
        title: String,
        message: String,
        smallIcon: Int = android.R.drawable.ic_dialog_info
    ) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        NotificationManagerCompat.from(context).notify(notificationId, builder.build())
    }

    fun scheduleNotification(
        delay: Long = 10000,
        channelId: String = "default_channel",
        notificationId: Int = 0,
        title: String = "Notification",
        message: String = "This is a notification.",
        smallIcon: Int = android.R.drawable.ic_dialog_info
    ) {
        val data = workDataOf(
            "channelId" to channelId,
            "title" to title,
            "message" to message,
            "notificationId" to notificationId,
            "smallIcon" to smallIcon
        )

        val workRequest = OneTimeWorkRequestBuilder<CustomNotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("notification_work")
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
