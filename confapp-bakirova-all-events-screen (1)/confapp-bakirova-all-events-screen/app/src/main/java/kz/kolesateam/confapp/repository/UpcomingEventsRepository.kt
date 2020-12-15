package kz.kolesateam.confapp.repository

import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.presentation.ResponseData

interface UpcomingEventsRepository {
    fun getUpcomingEvents() : ResponseData<List<UpcomingEventsListItem>, Exception>
}