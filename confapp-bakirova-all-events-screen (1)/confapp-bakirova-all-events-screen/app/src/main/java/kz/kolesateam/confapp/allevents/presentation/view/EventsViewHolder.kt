package kz.kolesateam.confapp.allevents.presentation.view

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.AllEventsListItem
import kz.kolesateam.confapp.clicklisteners.AllEventsClickListener
import kz.kolesateam.confapp.model.EventData
import java.text.SimpleDateFormat
import java.util.*

const val TIME_FORMAT = "HH:mm"
const val TIME_FORMAT_IN_CARD = "%s - %s • %s"

class EventsViewHolder(
    view: View,
    private val allEventsClickListener: AllEventsClickListener,
) : BaseViewHolder<AllEventsListItem>(view) {

    private val branchEvent: View =
        view.findViewById(R.id.activity_all_events_event_card)
    private val textViewStateEvent: TextView = branchEvent.findViewById<TextView>(
        R.id.event_state_text_view
    )
    private val eventPaddingLeft = branchEvent.paddingLeft
    private val eventPaddingTop = branchEvent.paddingTop
    private val eventPaddingRight = branchEvent.paddingRight
    private val eventPaddingBottom = branchEvent.paddingBottom

    private val timeAndPlaceTextView: TextView =
        branchEvent.findViewById(R.id.event_data_and_place_text_view)
    private val speakerFullNameTextView: TextView =
        branchEvent.findViewById(R.id.event_speaker_name_text_view)
    private val speakerJobTextView: TextView =
        branchEvent.findViewById(R.id.event_speaker_job_text_view)
    private val eventTitleTextView: TextView =
        branchEvent.findViewById(R.id.event_title_text_view)
    private val favoritesImageButton: ImageButton =
        branchEvent.findViewById(R.id.favorite_image_view)
    private var isFavorite: Boolean = false

    init {
        branchEvent.findViewById<TextView>(
            R.id.event_state_text_view
        ).visibility = View.INVISIBLE
    }

    override fun onBind(data: AllEventsListItem) {
        val event = (data as? AllEventsListItem.EventListItem)?.data ?: return
        fillEvent(event)
        setOnClickListeners(event)
    }
    private fun fillEvent(
        eventData: EventData,
    ) {
        val simpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.ROOT)
        val dateNowFormat = simpleDateFormat.format(Date())
        val dateNow = simpleDateFormat.parse(dateNowFormat)!!
        timeAndPlaceTextView.text = formatStringForDateAndPlace(eventData)
        speakerFullNameTextView.text = eventData.speaker.fullName
        speakerJobTextView.text = eventData.speaker.job
        eventTitleTextView.text = eventData.title
        setBackgroundEvent(dateNow.after(eventData.endTime))
    }

    private fun setBackgroundEvent(isEndEvent: Boolean) {
        branchEvent.setPadding(
            eventPaddingLeft,
            eventPaddingTop,
            eventPaddingRight,
            eventPaddingBottom
        )
        if (isEndEvent) {
            textViewStateEvent.visibility = View.VISIBLE
            textViewStateEvent.setBackgroundResource(R.drawable.bg_event_completed)
            textViewStateEvent.text =
                textViewStateEvent.context.getString(R.string.all_events_state_text_view)
            branchEvent.setBackgroundResource(R.color.bg_next_event_color)

        } else {
            textViewStateEvent.visibility = View.INVISIBLE

        }
    }

    private fun formatStringForDateAndPlace(event: EventData): String {
        val simpleDateFormat = SimpleDateFormat(TIME_FORMAT, Locale.ROOT)
        val startTime = simpleDateFormat.format(event.startTime)
        val endTime = simpleDateFormat.format(event.endTime)
        return String.format(
            TIME_FORMAT_IN_CARD,
            startTime,
            endTime,
            event.place
        )
    }

    private fun setOnClickListeners(event: EventData) {

        branchEvent.setOnClickListener {
            allEventsClickListener.onEventClick(
                event
            )
        }

        favoritesImageButton.setOnClickListener {
            event
            val favoriteImageResource = getFavoriteImageResource(event.isFavorite)
            favoritesImageButton.setImageResource(favoriteImageResource)
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