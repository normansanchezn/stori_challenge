package com.example.storichallenge.modules.account.di

import com.example.storichallenge.data.database.local.dao.AccountDao
import com.example.storichallenge.modules.account.data.dataSource.local.LocalAccountDS
import com.example.storichallenge.modules.account.data.dataSource.remote.RemoteAccountDS
import com.example.storichallenge.modules.account.data.repository.AccountRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object AccountModule {

    @Provides
    fun provideLocalAccountDataSource(
        localDataSource: AccountDao
    ) = LocalAccountDS(localDataSource)

    @Provides
    fun provideRemoteAccountDataSource(
        firebaseAuthInstance: FirebaseAuth,
        firestoreInstance: FirebaseFirestore,
        dispatcher: CoroutineDispatcher
    ) = RemoteAccountDS(
        firebaseAuthInstance,
        firestoreInstance,
        dispatcher
    )

    @Provides
    fun provideAccountRepository(
        localAccountDS: LocalAccountDS,
        remoteAccountDS: RemoteAccountDS
    ) = AccountRepository(
        localAccountDS,
        remoteAccountDS
    )
}