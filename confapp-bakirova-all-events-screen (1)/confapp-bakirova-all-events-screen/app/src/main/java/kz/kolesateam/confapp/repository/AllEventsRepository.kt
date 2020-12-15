package kz.kolesateam.confapp.repository

import kz.kolesateam.confapp.allevents.AllEventsListItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.presentation.ResponseData

interface AllEventsRepository {
    fun getAllEvents(branchId: Int, branchTitle: String) : ResponseData<List<AllEventsListItem>, Exception>
}