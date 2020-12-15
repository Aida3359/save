package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.repository.DefaultFavoritesRepository
import kz.kolesateam.confapp.repository.FavoritesRepository
import kz.kolesateam.confapp.favorite.presentation.FavoriteEventsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val favoriteEventsModule: Module = module {
    viewModel {
        FavoriteEventsViewModel(
            favoritesRepository = get(),
            notificationAlarmHelper = get()
        )
    }

    single<FavoritesRepository> {
        DefaultFavoritesRepository(
            context = androidContext(),
            objectMapper = get()
        )
    }
}