package com.example.passwordmanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "passwords")
@Parcelize
data class Password(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val website: String,
    val username: String,
    val password: String
): Parcelable
