package com.example.storichallenge.modules.account.data.dataSource.remote

import android.util.Log
import com.example.storichallenge.constants.StoriConstants.COLLECTION_ACCOUNTS
import com.example.storichallenge.constants.StoriConstants.EMAIL
import com.example.storichallenge.constants.StoriConstants.ID_PHOTO
import com.example.storichallenge.constants.StoriConstants.LAST_NAME
import com.example.storichallenge.constants.StoriConstants.NAME
import com.example.storichallenge.constants.StoriConstants.PASSWORD
import com.example.storichallenge.extensions.TAG
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteAccountDS @Inject constructor(
    private val firebaseAuthInstance: FirebaseAuth,
    private val firestoreInstance: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher
): RemoteAccountDataSource {
    override suspend fun createRemoteUserAuth(email: String?, password: String?): Flow<FirebaseResult> {
        return callbackFlow {
            if (email == null || password == null) {
                trySend(FirebaseResult.FirebaseErrorOperation)
                close()
            }
            firebaseAuthInstance
                .createUserWithEmailAndPassword(
                    email!!, password!!
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(FirebaseResult.FirebaseSuccessOperation)
                        close()
                    } else {
                        trySend(FirebaseResult.FirebaseErrorOperation)
                        close()
                    }
                }
            awaitClose { cancel() }
        }.flowOn(dispatcher)
            .conflate()
    }

    override suspend fun saveRemoteAccount(account: Account?): Flow<FirebaseResult> {
        return callbackFlow {
            firestoreInstance.collection(COLLECTION_ACCOUNTS)
                .document(account?.email!!).set(
                    hashMapOf(
                        EMAIL to account.email,
                        ID_PHOTO to account.idPhoto,
                        NAME to account.name,
                        LAST_NAME to account.lastName,
                        PASSWORD to account.password
                    )
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(FirebaseResult.FirebaseSuccessOperation)
                        close()
                    } else {
                        trySend(FirebaseResult.FirebaseErrorOperation)
                        close()
                    }
                }
            awaitClose { cancel() }
        }.flowOn(dispatcher)
            .conflate()
    }

    override suspend fun loginWithAccount(email: String, password: String): Flow<FirebaseResult> {
        return callbackFlow {
            firebaseAuthInstance
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(FirebaseResult.FirebaseSuccessOperation)
                        close()
                    } else {
                        trySend(FirebaseResult.FirebaseErrorOperation)
                        close()
                    }
                }
            awaitClose { cancel() }
        }.flowOn(dispatcher)
            .conflate()
    }
}