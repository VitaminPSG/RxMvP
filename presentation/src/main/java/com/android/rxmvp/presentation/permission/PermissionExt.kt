package com.android.rxmvp.presentation.permission

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.subjects.PublishSubject

fun Activity.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.isRevoked(permission: String): Boolean {
    return packageManager.isPermissionRevokedByPolicy(
        permission,
        packageName
    )
}

fun Fragment.isRevoked(permission: String): Boolean {
    val activity = requireActivity()
    return activity.packageManager.isPermissionRevokedByPolicy(
        permission,
        activity.packageName
    )
}

fun Fragment.handlePermission(
    permissions: Array<String>,
    grantResults: IntArray,
    subject: PublishSubject<List<Permission>>
) {
    val shouldShowRequestPermissionRationale = BooleanArray(permissions.size)
    for (i in permissions.indices) {
        shouldShowRequestPermissionRationale[i] =
            shouldShowRequestPermissionRationale(permissions[i])
    }
    subject.onNext(permissions.mapIndexed { index, name ->
        Permission(
            name,
            grantResults[index] == PackageManager.PERMISSION_GRANTED,
            shouldShowRequestPermissionRationale[index]
        )
    })
}