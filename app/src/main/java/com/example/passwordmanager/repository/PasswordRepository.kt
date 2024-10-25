package com.example.passwordmanager.repository

import com.example.passwordmanager.database.PasswordDatabase
import com.example.passwordmanager.model.Password

class PasswordRepository(private val db: PasswordDatabase) {

    suspend fun insertPassword(password: Password) = db.getPasswordDao().insertPassword(password)
    suspend fun deletePassword(password: Password) = db.getPasswordDao().deletePassword(password)
    suspend fun updatePassword(password: Password) = db.getPasswordDao().updatePassword(password)

    fun getAllPasswords() = db.getPasswordDao().getAllPasswords()
    fun searchPassword(query: String?) = db.getPasswordDao().searchPassword(query)
}