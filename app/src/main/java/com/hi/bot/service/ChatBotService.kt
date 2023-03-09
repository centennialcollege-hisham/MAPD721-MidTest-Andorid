package com.hi.bot.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.hi.bot.notification.MyNotificationHelper

class ChatBotService : Service() {

    private val TAG = "ChatBotService"

    companion object {
        const val CMD = "cmd"
        const val CMD_GENERATE_MSGS = 1
        const val ACTION_GENERATE_MESSAGE = "SERVICE_GENERATE_MESSAGE"
        const val CMD_STOP_SERVICE = 64
        const val MESSAGE_TEXT = "message"
        const val NAME = "name"
    }

    private lateinit var notificationManager: MyNotificationHelper

    private lateinit var chatMessages: List<String>

    private fun getGenerateMessages(name: String) = listOf(
        "Hello $name!", "How are you?", "Goodbye $name!"
    )

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(TAG, "onCreate()")
        notificationManager = MyNotificationHelper(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.v(TAG, "onStartCommand()")
        if (intent != null) {
            val data = intent.extras

            chatMessages = getGenerateMessages(data?.getString(NAME)!!)

            val command = data.getInt(CMD) ?: return START_STICKY

            when (command) {
                CMD_GENERATE_MSGS -> {
                    for (item in chatMessages) {
                        notificationManager.displaySimpleNotification("Generating message...", item)
                        sendBroadcastConnected(item)
                    }
                }
                CMD_STOP_SERVICE -> {
                    notificationManager.displaySimpleNotification("ChatBot Stopped: ", CMD_STOP_SERVICE.toString())
                    stopSelf()
                }
                else -> Log.w(TAG, "Ignoring Unknown Command! id=$command")
            }
        }
        return START_STICKY
    }

    private fun sendBroadcastConnected(responseMessage: String?) {
        val responseIntent = Intent().apply {
            action = ACTION_GENERATE_MESSAGE
            putExtra("message", responseMessage)
        }
        sendBroadcast(responseIntent)
    }
}
