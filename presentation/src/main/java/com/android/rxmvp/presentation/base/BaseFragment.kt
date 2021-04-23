package com.android.rxmvp.presentation.base

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.android.rxmvp.presentation.permission.Permission
import com.android.rxmvp.presentation.permission.PermissionDelegate
import com.android.rxmvp.presentation.permission.handlePermission
import com.android.rxmvp.presentation.permission.isPermissionGranted
import com.android.rxmvp.presentation.permission.isRevoked
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseFragment<V, P : BasePresenter<V>>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId),
    PermissionDelegate {

    abstract val presenter: P
    abstract val abstractView: V

    protected val onClickBack: PublishSubject<Unit> = PublishSubject.create()
    private val permissionSubject: PublishSubject<List<Permission>> = PublishSubject.create()

    protected var isConsumeBackPress: () -> Boolean = {
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().let { activity ->
            activity.onBackPressedDispatcher.addCallback(this) {
                if (isConsumeBackPress()) {
                    isEnabled = false
                    activity.onBackPressed()
                    isEnabled = true
                }
                onClickBack.onNext(Unit)
            }
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewAttached(abstractView)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onViewDetached()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        presenter.onViewWillShow(abstractView)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        presenter.onViewWillHide()
    }

    // RxPermission region
    override fun isGranted(permission: String) = isPermissionGranted(permission)

    override fun isRevokedByPolicy(permission: String) = isRevoked(permission)

    override fun requestPermissions(permissions: Array<String>) {
        if (permissions.isEmpty()) {
            return
        }
        requestPermissions(permissions, PERMISSIONS_REQUEST_CODE)
    }

    override fun permissionsResult(): Observable<Array<Permission>> {
        return permissionSubject.map { it.toTypedArray() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != PERMISSIONS_REQUEST_CODE) return
        handlePermission(permissions, grantResults, permissionSubject)
    }
    // end RxPermission

    companion object {

        private const val PERMISSIONS_REQUEST_CODE = 2020
    }
}