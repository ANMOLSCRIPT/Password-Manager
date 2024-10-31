package com.example.passwordmanager.domain


import com.example.passwordmanager.data.PasswordDao
import com.example.passwordmanager.data.PasswordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject



class Repository @Inject constructor(
    private val passwordDao: PasswordDao
) {

    suspend fun addPassword(passwordEntity: PasswordEntity) = passwordDao.addPassword(passwordEntity)
    suspend fun updatePassword(passwordEntity: PasswordEntity) = passwordDao.updatePassword(
        password = passwordEntity.password,
        accountName = passwordEntity.accountName,
        userName = passwordEntity.userName,
        id = passwordEntity.id
    )
    suspend fun deletedPassword(passwordEntity: PasswordEntity)= passwordDao.deletePassword(passwordEntity)
    fun getAllPassword(): Flow<List< PasswordEntity>> = passwordDao.getAllPassword().flowOn(Dispatchers.IO).conflate()

}