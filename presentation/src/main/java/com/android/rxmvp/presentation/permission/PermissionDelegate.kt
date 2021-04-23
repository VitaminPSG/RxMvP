package com.android.rxmvp.presentation.permission

import com.android.rxmvp.presentation.permission.Permission
import io.reactivex.rxjava3.core.Observable

interface PermissionDelegate {
    fun isGranted(permission: String): Boolean
    fun isRevokedByPolicy(permission: String): Boolean
    fun requestPermissions(permissions: Array<String>)
    fun permissionsResult(): Observable<Array<Permission>>
}
