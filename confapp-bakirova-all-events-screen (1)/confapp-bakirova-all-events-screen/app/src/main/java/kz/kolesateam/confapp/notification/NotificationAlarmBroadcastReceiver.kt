package kz.kolesateam.confapp.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

const val DO_NOT_MISS_NEXT_EVENT = "Не пропустите следующий доклад"

class NotificationAlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val content = intent?.getStringExtra(NOTIFICATION_CONTENT_KEY).orEmpty()

        NotificationHelper.sendNotification(
            title = DO_NOT_MISS_NEXT_EVENT,
            content = content,
        )
    }
}