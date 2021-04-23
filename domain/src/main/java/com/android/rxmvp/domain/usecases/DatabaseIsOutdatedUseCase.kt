package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.models.Version
import com.android.rxmvp.domain.repositories.DatabaseRepository
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

class DatabaseIsOutdatedUseCase(
    private val databaseRepository: DatabaseRepository,
) {

    operator fun invoke(): Single<Boolean> {
        return databaseRepository.getDatabaseVersion()
            .toSingle()
            .map { version -> version.isStale }
            .onErrorResumeNext {
                Single.just(true)
            }
    }

    private val Version.isStale: Boolean
        get() {
            return differenceInHours(lastUpdatedTime) >= 2
        }

    private companion object {

        fun differenceInHours(previousDate: Long): Long {
            val diffInMillis = System.currentTimeMillis() - previousDate
            return TimeUnit.MILLISECONDS.toHours(diffInMillis.absoluteValue)
        }
    }
}