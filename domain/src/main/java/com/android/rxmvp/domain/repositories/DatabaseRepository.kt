package com.android.rxmvp.domain.repositories

import com.android.rxmvp.domain.models.Version
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

interface DatabaseRepository {

    fun getDatabaseVersion(): Maybe<Version>
    fun updateDatabaseVersion(): Completable
}