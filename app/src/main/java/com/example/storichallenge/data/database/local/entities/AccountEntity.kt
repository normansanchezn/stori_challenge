package com.example.storichallenge.data.database.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "id_photo") val idPhoto: String?,
)
