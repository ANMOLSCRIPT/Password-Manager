package com.example.passwordmanager.viewodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.model.Password
import com.example.passwordmanager.repository.PasswordRepository
import kotlinx.coroutines.launch

class PasswordViewModel(app: Application, private val passwordRepository: PasswordRepository): AndroidViewModel(app) {

    fun addPassword(password: Password) =
        viewModelScope.launch {
            passwordRepository.insertPassword(password)
        }

    fun deletePassword(password: Password) =
        viewModelScope.launch {
            passwordRepository.deletePassword(password)
        }

    fun updatePassword(password: Password) =
        viewModelScope.launch {
            passwordRepository.updatePassword(password)
        }

    fun getAllPasswords() = passwordRepository.getAllPasswords()

    fun searchPassword(query: String?) =
        passwordRepository.searchPassword(query)
}