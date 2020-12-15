package kz.kolesateam.confapp.allevents

import kz.kolesateam.confapp.model.EventData

const val EVENT_TYPE: Int = 2
const val BRANCH_TITLE_TYPE: Int = 3

sealed class AllEventsListItem(
    val type: Int,
) {
    data class EventListItem(
        val data: EventData,
    ) : AllEventsListItem(EVENT_TYPE)

    data class BranchTitleItem(
        val branchTitle: String,
    ) : AllEventsListItem(BRANCH_TITLE_TYPE)
}