package kz.kolesateam.confapp.presentation.utils.mappers

import kz.kolesateam.confapp.events.data.models.*
import kz.kolesateam.confapp.model.BranchData
import kz.kolesateam.confapp.model.EventData

private const val DEFAULT_BRANCH_NAME = "Название направления не указано"

class BranchApiDataMapper : Mapper<BranchApiData, BranchData> {
    private val eventMapper: Mapper<List<EventApiData>, List<EventData>> = EventApiDataMapper()
    override fun map(data: BranchApiData?): BranchData {
        return BranchData(
            id = data?.id ?: 0,
            title = data?.title ?: DEFAULT_BRANCH_NAME,
            events = eventMapper.map(data?.events)
        )
    }
}