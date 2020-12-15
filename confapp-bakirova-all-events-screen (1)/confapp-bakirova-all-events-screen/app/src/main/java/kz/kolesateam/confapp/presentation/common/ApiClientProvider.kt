package kz.kolesateam.confapp.presentation.common

import kz.kolesateam.confapp.events.data.datasource.UpcomingEventsDataSource
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object ApiClientProvider {
    private val apiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://37.143.8.68:2020")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
    val UPCOMING_EVENTS_DATA_SOURCE_PROVIDER: UpcomingEventsDataSource = apiRetrofit.create(
        UpcomingEventsDataSource::class.java)



    }
