package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.allevents.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import kz.kolesateam.confapp.repository.DefaultUpcomingEventsRepository
import kz.kolesateam.confapp.repository.AllEventsRepository
import kz.kolesateam.confapp.repository.DefaultAllEventsRepository
import kz.kolesateam.confapp.repository.UpcomingEventsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val eventScreenModule: Module = module {

    viewModel() {
        AllEventsViewModel(
            allEventsRepository = get(),
            favoritesRepository = get(),
            notificationAlarmHelper = get()
        )
    }

    single {
        val retrofit: Retrofit = get()

        retrofit.create(AllEventsDataSource::class.java)
    }

    factory<AllEventsRepository> {
        DefaultAllEventsRepository(
            allEventsDataSource = get()
        )
    }
}