package kz.kolesateam.confapp.events.data

import com.fasterxml.jackson.databind.JsonNode
import kz.kolesateam.confapp.events.data.models.BranchApiData
import retrofit2.Call
import retrofit2.http.GET

interface ApiClient {
    @GET("/upcoming_events")
    fun getUpcomingEvents(): Call<List<BranchApiData>>

    @GET("/upcoming_events")
    fun getUpcomingEventsSync(): Call<JsonNode>
}