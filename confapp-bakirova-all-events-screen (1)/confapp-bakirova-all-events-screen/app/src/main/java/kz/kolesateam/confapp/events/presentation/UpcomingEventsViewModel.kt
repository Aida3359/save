package kz.kolesateam.confapp.upcoming.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.kolesateam.confapp.events.data.models.BRANCH_TYPE
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.repository.FavoritesRepository
import kz.kolesateam.confapp.model.EventData
import kz.kolesateam.confapp.notification.NotificationAlarmHelper
import kz.kolesateam.confapp.presentation.ProgressState
import kz.kolesateam.confapp.presentation.ResponseData
import kz.kolesateam.confapp.repository.UpcomingEventsRepository

class UpcomingEventsViewModel(
    private val upcomingEventsRepository: UpcomingEventsRepository,
    private val favoritesRepository: FavoritesRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper,
) : ViewModel() {

    private val progressLiveData: MutableLiveData<ProgressState> = MutableLiveData()
    private val upcomingEventsLiveData: MutableLiveData<List<UpcomingEventsListItem>> =
        MutableLiveData()
    private val errorLiveData: MutableLiveData<Exception> = MutableLiveData()

    fun getProgressLiveData(): LiveData<ProgressState> = progressLiveData

    fun getUpcomingEventsLiveData(): LiveData<List<UpcomingEventsListItem>> = upcomingEventsLiveData

    fun getErrorLiveData(): LiveData<Exception> = errorLiveData

    fun onStart() {
        getUpcomingEvents()
    }

    private fun getUpcomingEvents() {
        viewModelScope.launch(Dispatchers.Main) {
            progressLiveData.value = ProgressState.Loading

            val upcomingEventsResponse: ResponseData<List<UpcomingEventsListItem>, Exception> =
                withContext(Dispatchers.IO) {
                    upcomingEventsRepository.getUpcomingEvents()
                }

            when (upcomingEventsResponse) {
                is ResponseData.Success -> {
                    upcomingEventsResponse.result.forEach {
                        if(it.type == BRANCH_TYPE) {
                            it as UpcomingEventsListItem.BranchListItem
                            it.data.events.forEach { data ->
                                data.isFavorite = favoritesRepository.isFavorite(data.id)
                            }
                        }
                    }
                    upcomingEventsLiveData.value =
                        upcomingEventsResponse.result
                }
                is ResponseData.Error -> errorLiveData.value = upcomingEventsResponse.error
            }

            progressLiveData.value = ProgressState.Done
        }
    }

    fun onFavoriteClick(eventData: EventData) {
        when(eventData.isFavorite) {
            true -> {
                favoritesRepository.saveFavoriteEvent(eventData)
                scheduleEvent(eventData)
            }

            else -> {
                favoritesRepository.removeFavoriteEvent(eventData.id)
                cancelNotificationEvent(eventData)
            }
        }
    }

    private fun scheduleEvent(eventData: EventData) {
        notificationAlarmHelper.createNotificationAlarm(eventData)
    }

    private fun cancelNotificationEvent(eventData: EventData) {
        notificationAlarmHelper.cancelNotificationAlarm(eventData)
    }
}