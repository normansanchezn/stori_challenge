package com.example.storichallenge.base.model

import android.os.Bundle
import androidx.annotation.AnyRes
import androidx.navigation.NavOptions

data class NavigationAction(
    @AnyRes val actionId: Int,
    val arguments: Bundle? = null,
    val navOptions: NavOptions? = null
)