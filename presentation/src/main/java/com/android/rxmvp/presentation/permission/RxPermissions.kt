package com.android.rxmvp.presentation.permission

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import androidx.annotation.RequiresApi
import io.reactivex.rxjava3.core.Observable
import java.util.*

data class RxPermissions(private val delegate: PermissionDelegate) {

    /**
     * Ask for permissions with return values as {@link Permission}.
     * All the granted permission results will be emitted as soons as requested, then
     * the corresponding results are emitted.
     *
     * In Android versions < M, all the results will contain the {@link Permission#granted}
     * as {@code true}
     *
     * @param permissions the list of permissions
     * @return the permission results
     */
    fun request(vararg permissions: String): Observable<Array<out Permission>> {
        if (!isMarshmallow()) {
            return Observable.just(
                Array(permissions.size) { index ->
                    Permission(
                        permissions[index],
                        granted = true,
                        shouldShowRequestPermissionRationale = false
                    )
                }
            )
        }
        val granted = ArrayList<Permission>()
        val unGranted = ArrayList<Permission>()

        for (permission in permissions) {
            if (isGranted(permission)) {
                granted += Permission(
                    permission,
                    granted = true,
                    shouldShowRequestPermissionRationale = false
                )
                continue
            }
            if (isRevokedByPolicy(permission)) {
                granted += Permission(
                    permission,
                    granted = false,
                    shouldShowRequestPermissionRationale = false
                )
                continue
            }
            unGranted += Permission(
                permission,
                granted = false,
                shouldShowRequestPermissionRationale = true
            )
        }
        delegate.requestPermissions(Array(unGranted.size) { unGranted[it].name })
        return Observable.just(Array(granted.size) { granted[it] })
            .concatWith(delegate.permissionsResult())
            .flatMap { arr -> Observable.fromArray(arr) }
    }

    /**
     * Ask for permissions with return values as {@link Permission}
     * This method skips the granted permissions and emits
     * only un-granted {@link Permission}
     *
     * @param permissions the list of permissions
     * @return array of {@link Permission}s with un-granted permissions only
     */
    fun requestSkipGranted(vararg permissions: String): Observable<Array<Permission>> {
        return request(*permissions).concatMap { arr ->
            val list = arr.filter { !it.granted }
            return@concatMap Observable.just(Array(list.size) { i -> list[i] })
        }
    }

    /**
     * Ask for permissions with return values as array of {@link Boolean}.
     *
     * @param permissions the list of permissions
     * @return array of value containing {@code true} for all granted permissions, and
     *         {@code false} for all un-granted permissions
     */
    fun requestSimple(vararg permissions: String): Observable<Array<Boolean>> {
        if (!isMarshmallow()) {
            return Observable.just(Array(permissions.size) { true })
        }
        return request(*permissions).concatMap { arr ->
            val list = arr.map { it.granted }
            return@concatMap Observable.just(Array(list.size) { i -> list[i] })
        }
    }

    /**
     * Checks if a permission is granted
     *
     * @throws IllegalStateException if fragment is not attached to the activity
     * @return true if permission is granted
     */
    private fun isGranted(permission: String) = delegate.isGranted(permission)

    /**
     * Checks if a permission is revoked
     *
     * @throws IllegalStateException if fragment is not attached to the activity
     * @return true if permission is revoked
     */
    @RequiresApi(M)
    private fun isRevokedByPolicy(permission: String) = delegate.isRevokedByPolicy(permission)

    /**
     * @return true if device is >= {@ Build.VERSION_CODES.M}
     */
    private fun isMarshmallow() = SDK_INT >= M
}