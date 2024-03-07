package com.example.storichallenge.di

import android.content.Context
import com.example.storichallenge.data.database.local.RoomDatabaseProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDispatcher() = Dispatchers.IO

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = RoomDatabaseProvider.getInstance(appContext)

    @Provides
    fun provideAccountDao(db: RoomDatabaseProvider) = db.accountDao()

    @Singleton
    @Provides
    fun providesFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesFirebaseFirestoreInstance() = Firebase.firestore

}