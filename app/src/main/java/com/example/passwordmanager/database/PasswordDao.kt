package com.example.passwordmanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.passwordmanager.model.Password

@Dao
interface PasswordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPassword(password: Password)

    @Update
    suspend fun updatePassword(password: Password)

    @Delete
    suspend fun deletePassword(password: Password)

    @Query("SELECT * FROM PASSWORDS ORDER BY id DESC")
    fun getAllPasswords(): LiveData<List<Password>>

    @Query("SELECT * FROM PASSWORDS WHERE website LIKE :query OR username LIKE :query")
    fun searchPassword(query: String?): LiveData<List<Password>>
}