package com.example.storichallenge.modules.account.data.model

sealed class RoomOperation {
    data object SuccessOperation : RoomOperation()
    data object ErrorOperation : RoomOperation()
}