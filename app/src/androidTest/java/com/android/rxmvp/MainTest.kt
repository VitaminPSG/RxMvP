package com.android.rxmvp

import android.net.Uri
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.agoda.kakao.intent.KIntent
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.android.rxmvp.data.datasources.api.EventService
import com.android.rxmvp.data.datasources.database.DatabaseService
import com.android.rxmvp.domain.models.FavoriteEvent
import com.android.rxmvp.domain.models.Version
import com.android.rxmvp.presentation.SingleActivity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyList
import org.mockito.Mockito.anyLong
import java.util.Date

@RunWith(AndroidJUnit4ClassRunner::class)
class MainTest : KoinTest {

    @get:Rule
    var intentRule = IntentsTestRule(SingleActivity::class.java, true, false)

    lateinit var mockModule: Module

    private val mockEvent: FavoriteEvent = Mockito.mock(FavoriteEvent::class.java).apply {
        `when`(id).thenReturn(ID)
        `when`(url).thenReturn(URL)
    }

    private val mockVersion: Version = Mockito.mock(Version::class.java).apply {
        `when`(lastUpdatedTime).thenReturn(System.currentTimeMillis())
    }

    private val mockedApiService: EventService = Mockito.mock(EventService::class.java).apply {
        `when`(getEvents()).thenReturn(Single.never())
    }

    private val events = listOf(buildEvent(0), buildEvent(1), buildEvent(2, true))

    private val mockedDatabaseService: DatabaseService = Mockito.mock(DatabaseService::class.java).apply {
        `when`(addEvents(anyList())).thenReturn(Completable.never())
        `when`(getDatabaseVersion()).thenReturn(Maybe.just(mockVersion))
        `when`(updateDatabaseVersion()).thenReturn(Completable.complete())

        `when`(getAllEvents()).thenReturn(Flowable.just(events))
        `when`(getAllFavoriteEvents()).thenReturn(Flowable.just(events.filter { it.isFavorite }))

        `when`(addFavorite(mockEvent)).thenReturn(Completable.never())
        `when`(deleteFavorite(anyLong())).thenReturn(Completable.never())
    }

    @Before
    fun setup() {
        mockModule = module {
            single(override = true) { mockedApiService }
            single(override = true) { mockedDatabaseService }
        }

        loadKoinModules(mockModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(mockModule)
    }

    @Test
    fun showDefaultEventTab() {
        intentRule.launchActivity(null)
        onScreen<EventsScreen> {
            list {
                isVisible()
                hasSize(events.size)

                val index = events.indexOfFirst { it.isFavorite }
                childAt<EventsScreen.Item>(index) {
                    ivFavorite {
                        isVisible()
                        // issue with drawable https://github.com/agoda-com/Kakao/issues/179
                        // hasDrawable(R.drawable.ic_round_star_border_24)
                    }
                    tvName {
                        containsText(events[index].name)
                    }
                }
            }
        }

        onScreen<BottomBarScreen> {
            tvEvents {
                isVisible()
                hasTextColor(R.color.green)
            }
        }
    }

    @Test
    fun onClickFavoritesTab() {
        intentRule.launchActivity(null)
        onScreen<EventsScreen> {
            list {
                isVisible()
                hasSize(events.size)
            }
        }

        onScreen<BottomBarScreen> {
            tvEvents {
                isVisible()
                hasTextColor(R.color.green)
            }
            tvFavorites {
                hasTextColor(R.color.gray)
                click()
                hasTextColor(R.color.green)
            }
        }
        onScreen<FavoritesScreen> {
            list {
                isVisible()
                hasSize(events.filter { it.isFavorite }.size)
            }
        }
    }

    @Test
    fun onClickViewCheckWebIntent() {
        intentRule.launchActivity(null)
        onScreen<EventsScreen> {
            list {
                isVisible()
                hasSize(events.size)

                firstChild<EventsScreen.Item> {
                    click()
                }
            }

            val webIntent = KIntent {
                hasData(Uri.parse(events.first().url))
            }
            webIntent.intended()
        }
    }

    @Test
    fun onSwipeToRefreshLoadNewEvents() {
        intentRule.launchActivity(null)
        onScreen<EventsScreen> {
            list {
                isVisible()
                hasSize(events.size)
            }

            refreshLayout {
                swipeDown()
            }
        }
    }

    companion object {

        const val ID = 1L
        const val URL = "url"

        private fun buildEvent(id: Long = 0, isFavorite: Boolean = false): FavoriteEvent {
            return object : FavoriteEvent {
                override val id: Long
                    get() = id
                override val name: String
                    get() = "Test event #$id"
                override val datetime: Date
                    get() = Date()
                override val url: String
                    get() = "test url $id"
                override val isFavorite: Boolean
                    get() = isFavorite

            }
        }
    }
}