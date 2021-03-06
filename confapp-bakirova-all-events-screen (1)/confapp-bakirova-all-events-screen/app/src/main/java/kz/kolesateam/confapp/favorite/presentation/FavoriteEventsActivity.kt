package kz.kolesateam.confapp.favorite.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.clicklisteners.AllEventsClickListener
import kz.kolesateam.confapp.events.presentation.UpcomingEventsRouter
import kz.kolesateam.confapp.favorite.presentation.view.FavoriteEventsAdapter
import kz.kolesateam.confapp.model.EventData
import org.koin.android.ext.android.inject

class FavoriteEventsActivity : AppCompatActivity(), AllEventsClickListener {
    private val favoriteEventsViewModel: FavoriteEventsViewModel by inject()
    private lateinit var recyclerView: RecyclerView
    private lateinit var goHomeButton: Button
    private lateinit var containerForEmptyActivity: View
    private val adapter = FavoriteEventsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_events)
        initViews()
        observeFavoriteEventsViewModel()
        favoriteEventsViewModel.onStart()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.activity_favorite_events_recycler_view)
        recyclerView.apply {
            this.adapter = this@FavoriteEventsActivity.adapter
        }
        goHomeButton = findViewById(R.id.activity_favorite_events_button_to_home)
        containerForEmptyActivity = findViewById(R.id.activity_favorite_events_content_for_empty_activity)
        setOnClickListeners()
    }

    private fun observeFavoriteEventsViewModel() {
        favoriteEventsViewModel.getAllFavoriteEventsLiveData().observe(this, ::showResult)
    }

    private fun showResult(allEventsList: List<EventData>) {
        if(allEventsList.isNotEmpty()) {
            containerForEmptyActivity.visibility = View.INVISIBLE
        } else {
            containerForEmptyActivity.visibility = View.VISIBLE
        }
        adapter.setList(allEventsList)
    }

    private fun setOnClickListeners() {
        goHomeButton.setOnClickListener{
            startActivity(UpcomingEventsRouter().createIntent(this))
            finish()
        }
    }

    override fun onEventClick(eventData: EventData) {
        Toast.makeText(this, "Название доклада: ${eventData.title}", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onFavoritesClicked(eventData: EventData) {
        favoriteEventsViewModel.onFavoriteClick(eventData)
    }
}