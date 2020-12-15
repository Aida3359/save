package kz.kolesateam.confapp.clicklisteners

import kz.kolesateam.confapp.model.EventData

interface EventsClickListener {
    fun onEventClick(
        eventData: EventData,
    )
}