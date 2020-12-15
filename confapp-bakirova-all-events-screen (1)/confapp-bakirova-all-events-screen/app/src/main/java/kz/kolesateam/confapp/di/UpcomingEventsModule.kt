package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.repository.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.repository.UpcomingEventsRepository
import kz.kolesateam.confapp.upcoming.presentation.UpcomingEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val upcomingEventsModule: Module = module {

    viewModel() {
        UpcomingEventsViewModel(
            upcomingEventsRepository = get(),

            favoritesRepository = get(),
            notificationAlarmHelper = get()
        )
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(UpcomingEventsDataSource::class.java)
    }

    factory<UpcomingEventsRepository> {
        DefaultUpcomingEventsRepository(
            upcomingEventsDataSource = get(),
            userNameDataSource = get()
        )
    }
}