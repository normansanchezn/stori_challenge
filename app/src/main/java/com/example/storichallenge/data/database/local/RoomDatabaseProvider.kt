package com.example.storichallenge.data.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.storichallenge.constants.StoriConstants
import com.example.storichallenge.data.database.local.dao.AccountDao

@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class RoomDatabaseProvider: RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile private var INSTANCE: RoomDatabaseProvider? = null

        fun getInstance(context: Context): RoomDatabaseProvider =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            RoomDatabaseProvider::class.java,
            StoriConstants.STORI_DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    }

}