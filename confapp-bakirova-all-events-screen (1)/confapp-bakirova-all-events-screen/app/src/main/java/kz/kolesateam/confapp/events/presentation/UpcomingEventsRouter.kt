package kz.kolesateam.confapp.events.presentation

import android.content.Context
import android.content.Intent
import kz.kolesateam.confapp.events.presentation.UpcomingEventsActivity

class UpcomingEventsRouter {
    fun createIntent(
        context: Context
    ): Intent = Intent(context, UpcomingEventsActivity::class.java)
}