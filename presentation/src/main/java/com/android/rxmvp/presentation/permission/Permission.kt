package com.android.rxmvp.presentation.permission

import io.reactivex.rxjava3.core.Observable


data class Permission(
    val name: String,
    val granted: Boolean,
    val shouldShowRequestPermissionRationale: Boolean
) {

    private fun combineName(permissions: List<Permission>): String {
        return Observable.fromIterable(permissions)
            .map { permission -> permission.name }.collectInto(StringBuilder()) { s, s2 ->
                if (s.isEmpty()) {
                    s.append(s2)
                } else {
                    s.append(", ").append(s2)
                }
            }.blockingGet().toString()
    }

    private fun combineGranted(permissions: List<Permission>): Boolean {
        return Observable.fromIterable(permissions)
            .all { permission -> permission.granted }
            .blockingGet()
    }

    private fun combineShouldShowRequestPermissionRationale(permissions: List<Permission>): Boolean {
        return Observable.fromIterable(permissions)
            .any { permission -> permission.shouldShowRequestPermissionRationale }
            .blockingGet()
    }
}