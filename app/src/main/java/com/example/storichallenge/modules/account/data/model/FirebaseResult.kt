package com.example.storichallenge.modules.account.data.model

sealed class FirebaseResult {
    object FirebaseSuccessOperation: FirebaseResult()
    object FirebaseErrorOperation: FirebaseResult()
    object FirebasePendingOperation: FirebaseResult()
}