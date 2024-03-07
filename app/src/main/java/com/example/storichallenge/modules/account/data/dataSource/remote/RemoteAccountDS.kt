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
import javax.inject.Inject

class RemoteAccountDS @Inject constructor(
    private val firebaseAuthInstance: FirebaseAuth,
    private val firestoreInstance: FirebaseFirestore
): RemoteAccountDataSource {
    override suspend fun createRemoteUserAuth(email: String?, password: String?): FirebaseResult =
        try {
            if (email == null && password == null) {
                FirebaseResult.FirebaseErrorOperation
            } else {
                firebaseAuthInstance
                    .createUserWithEmailAndPassword(
                        email!!, password!!
                    ).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            FirebaseResult.FirebaseSuccessOperation
                        } else {
                            FirebaseResult.FirebaseErrorOperation
                        }
                    }
                FirebaseResult.FirebasePendingOperation
            }
        } catch (e: Exception) {
            Log.i(TAG, "createRemoteUserAuth: ${e.message}")
            FirebaseResult.FirebaseErrorOperation
        }

    override suspend fun saveRemoteAccount(account: Account?): FirebaseResult =
        try {
            firestoreInstance.collection(COLLECTION_ACCOUNTS)
                .document(account?.email!!).set(
                    hashMapOf(
                        EMAIL to account.email,
                        ID_PHOTO to account.idPhoto,
                        NAME to account.name,
                        LAST_NAME to account.lastName,
                        PASSWORD to account.password
                    )
                )

            FirebaseResult.FirebaseSuccessOperation
        } catch (e: Exception) {
            FirebaseResult.FirebaseErrorOperation
        }
}