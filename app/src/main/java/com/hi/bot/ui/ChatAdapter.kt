package com.hi.bot.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hi.bot.databinding.RowChatBinding
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private val list: List<String>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: RowChatBinding) : RecyclerView.ViewHolder(binding.root) {

        var currentTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())



        fun bind(item: String) {
            binding.message = item
            binding.time = currentTime

        }
    }
}
