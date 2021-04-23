package com.android.rxmvp.domain.usecases

import com.android.rxmvp.domain.models.Version
import com.android.rxmvp.domain.repositories.DatabaseRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Maybe
import org.junit.Test
import java.util.concurrent.TimeUnit

class DatabaseIsOutdatedUseCaseTest {

    @Test
    fun `database is not exist`() {
        val mockedDatabaseRepository = mockk<DatabaseRepository>(relaxed = true)
        val useCase = DatabaseIsOutdatedUseCase(mockedDatabaseRepository)
        every {
            mockedDatabaseRepository.getDatabaseVersion()
        } returns Maybe.empty()

        useCase().test().assertValue(IS_OUTDATED)
    }

    @Test
    fun `database is exist but created 3h ago`() {
        val mockedDatabaseRepository = mockk<DatabaseRepository>(relaxed = true)
        val mockedVersion = mockk<Version>()

        val useCase = DatabaseIsOutdatedUseCase(mockedDatabaseRepository)
        every {
            mockedVersion.lastUpdatedTime
        } returns System.currentTimeMillis() - TimeUnit.HOURS.toMillis(3)

        every {
            mockedDatabaseRepository.getDatabaseVersion()
        } returns Maybe.just(mockedVersion)

        useCase().test().assertValue(IS_OUTDATED)
    }

    @Test
    fun `database is exist and created 1h ago`() {
        val mockedDatabaseRepository = mockk<DatabaseRepository>(relaxed = true)
        val mockedVersion = mockk<Version>()

        val useCase = DatabaseIsOutdatedUseCase(mockedDatabaseRepository)
        every {
            mockedVersion.lastUpdatedTime
        } returns System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1)

        every {
            mockedDatabaseRepository.getDatabaseVersion()
        } returns Maybe.just(mockedVersion)

        useCase().test().assertValue(UP_TO_DATE)
    }

    private companion object {

        const val IS_OUTDATED = true
        const val UP_TO_DATE = false
    }
}