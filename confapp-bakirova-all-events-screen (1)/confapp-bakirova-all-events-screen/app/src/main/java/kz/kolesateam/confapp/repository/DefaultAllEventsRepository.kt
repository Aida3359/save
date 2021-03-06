package kz.kolesateam.confapp.repository

import kz.kolesateam.confapp.allevents.AllEventsListItem
import kz.kolesateam.confapp.allevents.data.datasource.AllEventsDataSource
import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.presentation.ResponseData
import kz.kolesateam.confapp.presentation.utils.mappers.EventApiDataMapper

class DefaultAllEventsRepository(
    private val allEventsDataSource: AllEventsDataSource,
) : AllEventsRepository {

    override fun getAllEvents(branchId: Int, branchTitle: String): ResponseData<List<AllEventsListItem>, Exception> {
        try {
            val response = allEventsDataSource.getAllEvents(branchId).execute()

            if (response.isSuccessful) {
                val allEventsListItem: MutableList<AllEventsListItem> =
                    mutableListOf()

                val branchTitleListItem: AllEventsListItem =
                    AllEventsListItem.BranchTitleItem(branchTitle)

                val eventListItemList: List<AllEventsListItem> =
                    response.body()!!.map { eventApiData ->
                        AllEventsListItem.EventListItem(EventApiDataMapper().map(eventApiData))
                    }

                allEventsListItem.add(branchTitleListItem)
                allEventsListItem.addAll(eventListItemList)

                return ResponseData.Success(allEventsListItem)
            } else {
                return ResponseData.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            return ResponseData.Error(e)
        }
    }
}