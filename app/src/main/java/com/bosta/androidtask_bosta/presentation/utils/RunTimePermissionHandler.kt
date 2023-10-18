package com.bosta.androidtask_bosta.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


fun checkPermission(context: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        context, permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestPermission(activity: Activity, permissions: Array<String>, resultCode: Int) {
    ActivityCompat.requestPermissions(
        activity,
        permissions,
        resultCode
    )
}
