package com.hi.bot.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hi.bot.R
import com.hi.bot.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)


            if (binding.etName.text.toString().isNotEmpty()) {
                intent.putExtra("name", binding.etName.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.validation_name), Toast.LENGTH_SHORT).show()
            }

        }

    }

}