package com.example.storichallenge.modules.account.data.mappers

import com.example.storichallenge.data.database.local.entities.AccountEntity
import com.example.storichallenge.modules.account.data.model.Account
import com.example.storichallenge.utils.Map

class AccountMapper: Map<AccountEntity?, Account?> {
    override fun map(data: AccountEntity?): Account? {
        val entity = data ?: return null
        return Account(
            email = entity.email,
            name = entity.name,
            lastName = entity.lastName,
            idPhoto = entity.idPhoto,
            password = entity.password
        )
    }
}