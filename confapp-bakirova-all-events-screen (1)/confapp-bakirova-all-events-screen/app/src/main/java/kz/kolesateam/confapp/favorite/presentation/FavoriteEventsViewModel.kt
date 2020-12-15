package kz.kolesateam.confapp.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kolesateam.confapp.repository.FavoritesRepository
import kz.kolesateam.confapp.model.EventData
import kz.kolesateam.confapp.notification.NotificationAlarmHelper

class FavoriteEventsViewModel(
    private val favoritesRepository: FavoritesRepository,
    private val notificationAlarmHelper: NotificationAlarmHelper,
): ViewModel() {
    private val allFavoriteEventsLiveData: MutableLiveData<List<EventData>> =
        MutableLiveData()

    fun onStart() {
        getAllFavoriteEvents()
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

    fun getAllFavoriteEventsLiveData(): LiveData<List<EventData>> = allFavoriteEventsLiveData

    private fun scheduleEvent(eventData: EventData) {
        notificationAlarmHelper.createNotificationAlarm(eventData)
    }

    private fun cancelNotificationEvent(eventData: EventData) {
        notificationAlarmHelper.cancelNotificationAlarm(eventData)
    }

    private fun getAllFavoriteEvents() {
        allFavoriteEventsLiveData.value = favoritesRepository.getAllFavoriteEvents()
    }
}