package com.example.storichallenge.data.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storichallenge.data.database.local.entities.AccountEntity

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAccount(accountEntity: AccountEntity)

    @Query("SELECT * FROM AccountEntity limit 1")
    suspend fun getAccount(): AccountEntity?

    @Query("UPDATE AccountEntity " +
            "SET name=:name," +
            "last_name=:lastName " +
            "WHERE email=:email")
    suspend fun updateNameAndLastName(email: String?, name: String, lastName: String)

    @Query("UPDATE AccountEntity " +
            "SET password=:password " +
            "WHERE email=:email")
    suspend fun updatePassword(email: String?, password: String)

    @Query("UPDATE AccountEntity " +
            "SET id_photo=:idPhoto " +
            "WHERE email=:email")
    suspend fun updateIdPhoto(email: String?, idPhoto: String?)

    @Query("DELETE FROM AccountEntity")
    suspend fun delete()

}