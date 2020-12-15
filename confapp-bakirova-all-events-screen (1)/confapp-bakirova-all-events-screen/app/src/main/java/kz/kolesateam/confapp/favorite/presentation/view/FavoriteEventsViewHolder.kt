package kz.kolesateam.confapp.favorite.presentation.view

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.clicklisteners.AllEventsClickListener
import kz.kolesateam.confapp.allevents.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.model.EventData
import java.text.SimpleDateFormat
import java.util.*

class FavoriteEventsViewHolder(
    view: View,
    private val allEventsClickListener: AllEventsClickListener,
) : BaseViewHolder<EventData>(view) {

    private val favoriteEvent: View =
        view.findViewById(R.id.activity_all_events_event_card)

    private val timeAndPlaceTextView: TextView =
        favoriteEvent.findViewById(R.id.event_data_and_place_text_view)
    private val speakerFullNameTextView: TextView =
        favoriteEvent.findViewById(R.id.event_speaker_name_text_view)
    private val speakerJobTextView: TextView =
        favoriteEvent.findViewById(R.id.event_speaker_job_text_view)
    private val eventTitleTextView: TextView =
        favoriteEvent.findViewById(R.id.event_title_text_view)
    private val toFavoritesImageButton: ImageButton =
        favoriteEvent.findViewById(R.id.favorite_image_view)

    init {
        favoriteEvent.findViewById<TextView>(
            R.id.event_state_text_view
        ).visibility = View.INVISIBLE
    }

    override fun onBind(eventData: EventData) {
        fillEvent(eventData)
        setOnClickListeners(eventData)

    }

    private fun fillEvent(
        eventData: EventData,
    ) {
        val favoriteImageResource = getFavoriteImageResource(eventData.isFavorite)

        timeAndPlaceTextView.text = formatStringForDateAndPlace(eventData)
        speakerFullNameTextView.text = eventData.speaker.fullName
        speakerJobTextView.text = eventData.speaker.job
        eventTitleTextView.text = eventData.title
        toFavoritesImageButton.setImageResource(favoriteImageResource)
    }

    private fun formatStringForDateAndPlace(event: EventData): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
        val startTime = simpleDateFormat.format(event.startTime)
        val endTime = simpleDateFormat.format(event.endTime)
        return String.format(
            "%s - %s • %s",
            startTime,
            endTime,
            event.place
        )
    }

    private fun setOnClickListeners(event: EventData) {

        favoriteEvent.setOnClickListener {
            allEventsClickListener.onEventClick(
                event
            )
        }

        toFavoritesImageButton.setOnClickListener {
            event.isFavorite = !event.isFavorite
            val favoriteImageResource = getFavoriteImageResource(event.isFavorite)
            toFavoritesImageButton.setImageResource(favoriteImageResource)
            allEventsClickListener.onFavoritesClicked(event)
        }

    }
    private fun getFavoriteImageResource(
        isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_fill
        else -> R.drawable.ic_favorite_border
    }
}