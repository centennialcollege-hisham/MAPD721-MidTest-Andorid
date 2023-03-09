package com.hi.bot.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hi.bot.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getStringExtra("name")?.let {
            viewModel.startService(it)
        }

        binding.buttonStart.setOnClickListener {
            viewModel.generateMessages()
        }

        binding.fabSend.setOnClickListener {
            // TODO Next Version
        }

        binding.buttonStop.setOnClickListener {
            viewModel.stopService()
        }

        viewModel.messageLiveData.observe(this) {
            binding.recyclerView.adapter = ChatAdapter(it)
            binding.recyclerView.layoutManager?.scrollToPosition(it.size - 1)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregisterReceiver()
    }
}