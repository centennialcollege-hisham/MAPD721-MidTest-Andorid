package com.hi.bot.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hi.bot.R
import com.hi.bot.ui.LoginActivity

class MyNotificationHelper(private val context: Context) {
    var count = 0
    private val channelId = "com.hi.bot.1"
    private val channelName = "com.hi.bot"
    private val channelDesc = "chat message"
    private val notificationManager = NotificationManagerCompat.from(context)

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDesc
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun displaySimpleNotification(title: String, contentText: String?) {
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        val intent = Intent(context, LoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        builder.setContentIntent(pendingIntent)
        val notificationId = count++
        notificationManager.notify(notificationId, builder.build())
    }
}
