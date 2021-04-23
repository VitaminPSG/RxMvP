package com.android.rxmvp.presentation

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.android.rxmvp.presentation.databinding.ActivitySingleBinding
import com.android.rxmvp.presentation.events.EventsFragmentDirections
import com.android.rxmvp.presentation.favorites.FavoritesFragmentDirections
import com.android.rxmvp.presentation.navigation.NavigationIntent
import com.android.rxmvp.presentation.navigation.Screen
import com.android.rxmvp.presentation.utils.createCustomTab
import io.reactivex.rxjava3.core.Observable
import org.koin.android.ext.android.inject
import timber.log.Timber

class SingleActivity : AppCompatActivity(), SinglePresenter.View {

    private lateinit var binding: ActivitySingleBinding
    private lateinit var navController: NavController

    private val presenter: SinglePresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment)

    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttached(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewWillShow(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewWillHide()
    }

    override fun onStop() {
        super.onStop()
        presenter.onViewDetached()
    }

    override fun onScreenChanged(): Observable<Screen> {
        return Observable.create { emitter ->
            val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.eventsFragment -> {
                        emitter.onNext(NavigationIntent.OpenEvents)
                    }
                    R.id.favoritesFragment -> {
                        emitter.onNext(NavigationIntent.OpenFavorites)
                    }
                }
            }
            navController.addOnDestinationChangedListener(listener)
            emitter.setCancellable {
                navController.removeOnDestinationChangedListener(listener)
            }
        }
    }

    override fun navigateTo(intent: NavigationIntent) = try {
        when (intent) {
            NavigationIntent.OpenEvents -> navController.navigate(FavoritesFragmentDirections.openEventsFragment())
            NavigationIntent.OpenFavorites -> navController.navigate(EventsFragmentDirections.openFavoritesFragment())
            is NavigationIntent.OpenWeb -> {
                val customTabsIntent: CustomTabsIntent = CustomTabsIntent.Builder()
                    .createCustomTab(this)
                    .build()
                customTabsIntent.launchUrl(this, Uri.parse(intent.url))
            }
        }
    } catch (ex: Throwable) {
        Timber.e(ex, "Can't navigate to $intent")
    }
}