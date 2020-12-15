package kz.kolesateam.confapp.allevents.presentation.view

import android.view.View
import android.widget.*
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.AllEventsListItem
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem

class BranchTitleViewHolder(view: View) : BaseViewHolder<AllEventsListItem>(view) {

    private val branchNameTextView: TextView =
        view.findViewById(R.id.events_branch_title_text_view)

    override fun onBind(data: AllEventsListItem) {
        branchNameTextView.text = (data as? AllEventsListItem.BranchTitleItem)?.branchTitle
    }
}