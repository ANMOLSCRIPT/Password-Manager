package com.example.passwordmanager.viewodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanager.repository.PasswordRepository

class PasswordViewModelFactory(
    val app: Application,
    private val passwordRepository: PasswordRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordViewModel::class.java)) {
            return PasswordViewModel(app, passwordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
