package kz.kolesateam.confapp.favorite.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.clicklisteners.AllEventsClickListener
import kz.kolesateam.confapp.allevents.presentation.view.BaseViewHolder
import kz.kolesateam.confapp.model.EventData

class FavoriteEventsAdapter(
    private val allEventsClickListener: AllEventsClickListener,
) : RecyclerView.Adapter<BaseViewHolder<EventData>>()  {
    private val favoriteEventsList: MutableList<EventData> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<EventData> {
        return FavoriteEventsViewHolder(
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.all_events_branch_item, parent, false),
            allEventsClickListener = allEventsClickListener
        )

    }

    override fun onBindViewHolder(holder: BaseViewHolder<EventData>, position: Int) {
        holder.onBind(favoriteEventsList[position])
    }

    override fun getItemCount(): Int = favoriteEventsList.size

    fun setList(favoriteEventsList: List<EventData>) {
        this.favoriteEventsList.clear()
        this.favoriteEventsList.addAll(favoriteEventsList)
        notifyDataSetChanged()
    }
}