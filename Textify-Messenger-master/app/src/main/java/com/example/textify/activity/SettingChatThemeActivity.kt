package com.example.textify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.textify.databinding.ActivitySettingChatThemeBinding
import com.example.textify.viewmodels.SettingChatThemeViewModel

class SettingChatThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingChatThemeBinding
    private lateinit var viewModel: SettingChatThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingChatThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SettingChatThemeViewModel::class.java]
    }
}