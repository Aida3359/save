package kz.kolesateam.confapp.events.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.presentation.AllEventsRouter
import kz.kolesateam.confapp.events.data.models.UpcomingEventsListItem
import kz.kolesateam.confapp.events.presentation.view.EventsAdapter
import kz.kolesateam.confapp.clicklisteners.UpcomingItemsClickListener
import kz.kolesateam.confapp.favorite.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.model.BranchData
import kz.kolesateam.confapp.model.EventData
import kz.kolesateam.confapp.presentation.ProgressState
import kz.kolesateam.confapp.upcoming.presentation.UpcomingEventsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingEventsActivity : AppCompatActivity(), UpcomingItemsClickListener {

    private val upcomingEventsViewModel: UpcomingEventsViewModel by viewModel()

    private val adapter = EventsAdapter(this)
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var toFavoriteButton: Button
    private lateinit var favoriteButtonImage: ImageButton
    private var ifFavoriteButtonClicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_events)
        bindViews()
        observeUpcomingEventsViewModel()
        upcomingEventsViewModel.onStart()
    }

    private fun observeUpcomingEventsViewModel() {
        upcomingEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        upcomingEventsViewModel.getUpcomingEventsLiveData().observe(this, ::showResult)
        upcomingEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun showError(errorMessage: Exception) {
        val toastError = Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT)
        toastError.show()
    }

    private fun showResult(upcomingEventsList: List<UpcomingEventsListItem>) {
        adapter.setList(upcomingEventsList)
    }

    private fun handleProgressBarState(progressState: ProgressState?) {
        progressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun bindViews() {
        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.activity_upcoming_events_recycler_view)
        recyclerView.apply {
            this.adapter = this@UpcomingEventsActivity.adapter
            this.layoutManager = LinearLayoutManager(this@UpcomingEventsActivity)
        }

        toFavoriteButton = findViewById(R.id.bottom_button_to_favorites)

        toFavoriteButton.setOnClickListener {
            val intent = Intent(this, FavoriteEventsActivity::class.java)
            startActivity(intent)
        }
    }








    override fun onBranchClick(branchData: BranchData) {
        val intent = AllEventsRouter().createIntent(this@UpcomingEventsActivity)
        intent.putExtra("branch_id", branchData.id)
        intent.putExtra("branch_title", branchData.title)
        startActivity(intent)
    }

    override fun onEventClick(eventData: EventData) {
        val toast = Toast.makeText(
            this@UpcomingEventsActivity,
            "Текущий доклад: ${eventData.title} ",
            Toast.LENGTH_SHORT
        )
        toast.show()
    }

    override fun onFavoritesClicked(eventData: EventData) {
        upcomingEventsViewModel.onFavoriteClick(eventData)
    }
}


