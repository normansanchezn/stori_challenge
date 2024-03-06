package com.example.storichallenge.di

import android.content.Context
import com.example.storichallenge.data.database.local.RoomDatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = RoomDatabaseProvider.getInstance(appContext)

    @Provides
    fun provideAccountDao(db: RoomDatabaseProvider) = db.accountDao()

}