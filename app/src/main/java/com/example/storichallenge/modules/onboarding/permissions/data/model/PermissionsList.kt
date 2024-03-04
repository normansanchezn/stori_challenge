package com.example.storichallenge.modules.onboarding.permissions.data.model

import android.Manifest
import android.os.Build

object PermissionsList {
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.USE_BIOMETRIC,
            Manifest.permission.CAMERA,
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
        )
    }
}