package com.android.rxmvp.data.repositories

import com.android.rxmvp.data.datasources.database.DatabaseService
import com.android.rxmvp.domain.models.Version
import com.android.rxmvp.domain.repositories.DatabaseRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

class DatabaseRepositoryImpl(
    private val databaseService: DatabaseService
) : DatabaseRepository {

    override fun getDatabaseVersion(): Maybe<Version> {
        return databaseService.getDatabaseVersion()
    }

    override fun updateDatabaseVersion(): Completable {
        return databaseService.updateDatabaseVersion()
    }
}