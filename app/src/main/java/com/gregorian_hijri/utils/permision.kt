package com.gregorian_hijri.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun calenderPermission(activity: Activity, callbackId: Int, vararg permissionsId: String):Boolean {
    var permissions = true
    for (p in permissionsId) {
        permissions =
            permissions && ContextCompat.checkSelfPermission(activity, p) == PackageManager.PERMISSION_GRANTED
    }
    if (!permissions) ActivityCompat.requestPermissions(activity, permissionsId, callbackId)
    return permissions
}