package kz.kolesateam.confapp.repository

import kz.kolesateam.confapp.model.EventData

interface FavoritesRepository {
    fun saveFavoriteEvent(
        eventData: EventData
    )

    fun removeFavoriteEvent(
        eventId: Int
    )

    fun getAllFavoriteEvents(): List<EventData>

    fun isFavorite(id: Int): Boolean
}