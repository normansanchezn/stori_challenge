package com.example.storichallenge.extensions

import android.app.Activity
import android.view.WindowManager

fun Activity.setWindowsSecurityFlags() {
    this.window.setFlags(
        WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
    )
}