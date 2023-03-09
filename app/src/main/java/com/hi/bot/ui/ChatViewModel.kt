package com.hi.bot.ui

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hi.bot.service.ChatBotService
import com.hi.bot.service.ChatBotService.Companion.ACTION_GENERATE_MESSAGE

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val _message = MutableLiveData<List<String>>()
    val messageLiveData: LiveData<List<String>> = _message

    var tempMessages = ArrayList<String>()

    lateinit var nameArgument: String


    fun startService(name: String) {
        nameArgument = name
        startBroadCast()
        generateMessages()
    }

    private fun startBroadCast() {
        val intentFilter = IntentFilter().apply {
            addAction(ACTION_GENERATE_MESSAGE)
        }
        getApplication<Application>().registerReceiver(chatBotServiceResponseReceiver, intentFilter)
    }


    fun generateMessages() {
        executeServiceCommand(ChatBotService.CMD_GENERATE_MSGS)
    }

    fun stopService() {
        executeServiceCommand(ChatBotService.CMD_STOP_SERVICE)
    }

    private fun executeServiceCommand(command: Int) {
        val data = Bundle().apply {
            putInt(ChatBotService.CMD, command)
            putString(ChatBotService.NAME, nameArgument)
        }
        val intent = Intent(getApplication(), ChatBotService::class.java).apply {
            putExtras(data)
        }
        getApplication<Application>().startService(intent)
    }

    fun unregisterReceiver() {
        getApplication<Application>().unregisterReceiver(chatBotServiceResponseReceiver)
    }

    private val chatBotServiceResponseReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { it ->
                val valueString = it.getStringExtra("message")
                valueString?.let { tempMessages.add(it) }
                _message.value = tempMessages
            }
        }
    }

}
