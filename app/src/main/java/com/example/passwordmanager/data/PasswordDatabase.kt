package com.example.passwordmanager.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordmanager.data.PasswordEntity



@Database(
    entities = [PasswordEntity::class],
    version = 3,
    exportSchema = false

)
abstract class PasswordDatabase:RoomDatabase() {
    abstract fun PasswordDao():PasswordDao
}