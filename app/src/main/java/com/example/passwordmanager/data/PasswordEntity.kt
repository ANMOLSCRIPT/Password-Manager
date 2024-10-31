package com.example.passwordmanager.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("passwordManager")
data class PasswordEntity(
    @ColumnInfo("accountName")
    val accountName:String,
    @ColumnInfo("userName")
    val userName:String,
    @ColumnInfo("password")
    val password:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,

    )