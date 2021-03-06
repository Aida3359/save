package kz.kolesateam.confapp.details

import android.content.Context
import android.content.Intent

const val PUSH_NOTIFICATION_MESSAGE = "push_message"

class EventDetailsRouter {

    fun createIntent(
        context: Context
    ): Intent = Intent(context, EventDetailsActivity::class.java)

    fun createIntentForNotification(
        context: Context,
        messageFromPush: String,
    ): Intent = createIntent(context).apply {
        putExtra(PUSH_NOTIFICATION_MESSAGE, messageFromPush)
    }
}