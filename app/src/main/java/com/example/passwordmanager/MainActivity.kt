package com.example.passwordmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.database.PasswordDatabase
import com.example.passwordmanager.repository.PasswordRepository
import com.example.passwordmanager.viewodel.PasswordViewModel
import com.example.passwordmanager.viewodel.PasswordViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var passwordViewModel: PasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel(){
        val passwordRepository = PasswordRepository(PasswordDatabase(this))
        val viewModelProviderFactory = PasswordViewModelFactory(application, passwordRepository)
        passwordViewModel = ViewModelProvider(this, viewModelProviderFactory)[PasswordViewModel::class.java]
    }
}