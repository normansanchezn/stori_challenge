package com.example.storichallenge.modules.account.data.dataSource.remote

import com.example.storichallenge.constants.StoriConstants.COLLECTION_ACCOUNTS
import com.example.storichallenge.constants.StoriConstants.COLLECTION_BALANCE
import com.example.storichallenge.constants.StoriConstants.COLLECTION_TRANSACTIONS
import com.example.storichallenge.constants.StoriConstants.EMAIL
import com.example.storichallenge.constants.StoriConstants.ID_PHOTO
import com.example.storichallenge.constants.StoriConstants.LAST_NAME
import com.example.storichallenge.constants.StoriConstants.NAME
import com.example.storichallenge.constants.StoriConstants.PASSWORD
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.modules.account.data.model.AccountBalance
import com.example.storichallenge.modules.account.data.model.FirebaseResult
import com.example.storichallenge.modules.account.data.model.ResultFirebase
import com.example.storichallenge.modules.home.data.model.TransactionItem
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


    override suspend fun getTotalBalance(email: String?): Flow<ResultFirebase<AccountBalance>> {
        return callbackFlow {
            val docRef = firestoreInstance
                .collection(COLLECTION_BALANCE).document(email!!)

            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    trySend(
                        ResultFirebase.Success(
                            AccountBalance(
                                document.data?.get("totalBalance").toString()
                            )
                        )
                    )
                    close()
                } else {
                    trySend(ResultFirebase.Error("Hola", AccountBalance(null)))
                    close()
                }
            }
            awaitClose { cancel() }
        }.flowOn(dispatcher)
            .conflate()
    }

    override suspend fun getListOfTransactions(email: String?)
    : Flow<ResultFirebase<List<TransactionItem>>> {
        return callbackFlow {
            val docRef = firestoreInstance
                .collection(COLLECTION_TRANSACTIONS).document(email!!)

            docRef.get().addOnCompleteListener { document ->

                trySend(ResultFirebase.Success(
                    listOf(TransactionItem(
                        concept = null,
                        amount = null,
                        timestamp = null
                    ))
                ))
            }
            awaitClose { cancel() }
        }.flowOn(dispatcher)
            .conflate()
    }

}