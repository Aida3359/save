package kz.kolesateam.confapp.allevents.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.kolesateam.confapp.R
import kz.kolesateam.confapp.allevents.AllEventsListItem
import kz.kolesateam.confapp.allevents.presentation.view.AllEventsAdapter
import kz.kolesateam.confapp.clicklisteners.AllEventsClickListener
import kz.kolesateam.confapp.allevents.presentation.viewModel.AllEventsViewModel
import kz.kolesateam.confapp.favorite.presentation.FavoriteEventsActivity
import kz.kolesateam.confapp.model.EventData
import kz.kolesateam.confapp.presentation.ProgressState
import org.koin.androidx.viewmodel.ext.android.viewModel

const val BRANCH_IDS = 0
const val BRANCH_TITLES = ""


class AllEventsActivity() : AppCompatActivity(), AllEventsClickListener {
    private val allEventsViewModel: AllEventsViewModel by viewModel()
    private val adapter = AllEventsAdapter(this)

    private lateinit var eventsProgressBar: ProgressBar
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var goBackButton: ImageButton
    private lateinit var favoritesButton: Button

    private var branchId: Int = BRANCH_IDS
    private var branchTitle: String = BRANCH_TITLES
    private var isFavoriteButtonClicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_events)
        initViews()
        observeUpcomingEventsLiveData()
        allEventsViewModel.onStart(branchId, branchTitle)
    }

    private fun initViews() {
        branchId = intent.getIntExtra("BRANCH_ID", BRANCH_IDS)
        branchTitle = intent.getStringExtra("BRANCH_TITLE") ?: BRANCH_TITLES

        eventsProgressBar = findViewById(R.id.activity_all_events_progress_bar)
        goBackButton = findViewById(R.id.all_events_activity_button_go_back)
        favoritesButton = findViewById(R.id.bottom_button_to_favorites)

        eventsRecyclerView = findViewById(R.id.activity_all_events_recycler_view)
        eventsRecyclerView.apply {
            this.adapter = this@AllEventsActivity.adapter
            this.layoutManager = LinearLayoutManager(this@AllEventsActivity)
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        goBackButton.setOnClickListener {
            finish()
        }

        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoriteEventsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeUpcomingEventsLiveData() {
        allEventsViewModel.getProgressLiveData().observe(this, ::handleProgressBarState)
        allEventsViewModel.getAllEventsLiveData().observe(this, ::showResult)
        allEventsViewModel.getErrorLiveData().observe(this, ::showError)
    }

    private fun handleProgressBarState(
        progressState: ProgressState
    ) {
        eventsProgressBar.isVisible = progressState is ProgressState.Loading
    }

    private fun showError(errorMessage: Exception) {
        val toastError = Toast.makeText(this, errorMessage.localizedMessage, Toast.LENGTH_SHORT)
        toastError.show()
    }

    private fun showResult(allEventsList: List<AllEventsListItem>) {
        adapter.setList(allEventsList)
    }

    override fun onEventClick(eventData: EventData) {
        val toastEvent = Toast.makeText(
            this@AllEventsActivity,
            "Название доклада: ${eventData.title}",
            Toast.LENGTH_SHORT
        )
        toastEvent.show()
    }

    override fun onFavoritesClicked(eventData: EventData) {
        allEventsViewModel.onFavoriteClick(eventData)
    }
}