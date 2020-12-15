package kz.kolesateam.confapp.events.presentation.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.clicklisteners.UpcomingItemsClickListener
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.model.BranchData
import kz.kolesateam.confapp.model.EventData
import kz.kolesateam.confapp.model.SpeakerData
import java.text.SimpleDateFormat
import java.util.*

class BranchViewHolder(
    view: View,
    private val upcomingItemsClickListener: UpcomingItemsClickListener,
) : BaseViewHolder<UpcomingEventsListItem>(view)  {

    private val currentBranchEvent: View = itemView.findViewById(R.id.current_branch_event)
    private val nextBranchEvent: View = itemView.findViewById(R.id.next_branch_event)
    private val branchTitle: TextView = itemView.findViewById(R.id.branch_title)
    private val branchName: View = itemView.findViewById(R.id.activity_all_events_text_view_branch_name)

    private val arrowForward: View =
        itemView.findViewById(R.id.image_arrow_forward)
    private val eventDateAndPlaceCurrent: TextView =
        currentBranchEvent.findViewById(R.id.event_data_and_place_text_view)
    private val speakerNameCurrent: TextView =
        currentBranchEvent.findViewById(R.id.event_speaker_name_text_view)
    private val speakerJobCurrent: TextView =
        currentBranchEvent.findViewById(R.id.event_speaker_job_text_view)
    private val eventTitleCurrent: TextView =
        currentBranchEvent.findViewById(R.id.event_title_text_view)

    private val eventDateAndPlaceNext: TextView =
        nextBranchEvent.findViewById(R.id.event_data_and_place_text_view)
    private val speakerNameNext: TextView =
        nextBranchEvent.findViewById(R.id.event_speaker_name_text_view)
    private val speakerJobNext: TextView =
        nextBranchEvent.findViewById(R.id.event_speaker_job_text_view)
    private val eventTitleNext: TextView = nextBranchEvent.findViewById(R.id.event_title_text_view)

    private val currentEventFavoriteImageView: ImageView =
        currentBranchEvent.findViewById(R.id.favorite_image_view)
    private val nextEventFavoriteImageView: ImageView =
        nextBranchEvent.findViewById(R.id.favorite_image_view)
    private var isCurrentFavorite: Boolean = false
    private var isNextFavorite: Boolean = false

    init {
        currentBranchEvent.findViewById<TextView>(R.id.event_state_text_view).visibility =
            View.INVISIBLE
    }

    override fun onBind(data: UpcomingEventsListItem) {
        val branchData: BranchData =
            (data as? UpcomingEventsListItem.BranchListItem)?.data ?: return

        branchTitle.text = branchData.title

        val branchId = branchData.id
        val currentEvent = branchData.events!!.first()
        val nextEvent = branchData.events!!.last()
        val currentSpeaker = currentEvent?.speaker
        val nextSpeaker = nextEvent?.speaker

        val currentEventDateAndPlaceText = forDateAndPlace(currentEvent)
        val nextEventDateAndPlaceText = forDateAndPlace(nextEvent)

        setCurrentEventCard(currentEventDateAndPlaceText, currentSpeaker, currentEvent)
        setNextEventCard(nextEventDateAndPlaceText, nextSpeaker, nextEvent)

        setOnClickListeners(currentEvent, nextEvent, branchData)
    }

    private fun forDateAndPlace(event: EventData): String {
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

    private fun setCurrentEventCard(
        currentTime: String,
        currentSpeaker: SpeakerData,
        currentEvent: EventData
    ) {
        eventDateAndPlaceCurrent.text = currentTime
        speakerNameCurrent.text = currentSpeaker.fullName
        speakerJobCurrent.text = currentSpeaker.job
        eventTitleCurrent.text = currentEvent.title
    }

    private fun setNextEventCard(
        nextTime: String,
        nextSpeaker: SpeakerData,
        nextEvent: EventData
    ) {
        eventDateAndPlaceNext.text = nextTime
        speakerNameNext.text = nextSpeaker.fullName
        speakerJobNext.text = nextSpeaker.job
        eventTitleNext.text = nextEvent.title
    }

    private fun setOnClickListeners(
        currentEvent: EventData,
        nextEvent: EventData,
        branchData: BranchData
    ) {
        branchName.setOnClickListener {
            upcomingItemsClickListener.onBranchClick(
                branchData
            )
        }

        currentBranchEvent.setOnClickListener {
            upcomingItemsClickListener.onEventClick(
                nextEvent
            )
        }

        nextBranchEvent.setOnClickListener {
            upcomingItemsClickListener.onEventClick(
                nextEvent
            )
        }

        currentEventFavoriteImageView.setOnClickListener {
            currentEvent.isFavorite = !currentEvent.isFavorite
            val favoriteImageResource = getFavoriteImageResource(currentEvent.isFavorite)
            currentEventFavoriteImageView.setImageResource(favoriteImageResource)
            upcomingItemsClickListener.onFavoritesClicked(eventData = currentEvent)
        }

        nextEventFavoriteImageView.setOnClickListener {
            nextEvent.isFavorite = !nextEvent.isFavorite
            val favoriteImageResource = getFavoriteImageResource(nextEvent.isFavorite)
            nextEventFavoriteImageView.setImageResource(favoriteImageResource)
            upcomingItemsClickListener.onFavoritesClicked(eventData = nextEvent)
        }
    }

    private fun getFavoriteImageResource(
        isFavorite: Boolean
    ): Int = when (isFavorite) {
        true -> R.drawable.ic_favorite_fill
        else -> R.drawable.ic_favorite_border
    }
}