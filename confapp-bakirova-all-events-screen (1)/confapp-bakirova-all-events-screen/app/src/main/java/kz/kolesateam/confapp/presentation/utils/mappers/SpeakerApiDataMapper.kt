package kz.kolesateam.confapp.presentation.utils.mappers

import kz.kolesateam.confapp.events.data.models.SpeakerApiData
import kz.kolesateam.confapp.model.SpeakerData

private const val DEFAULT_SPEAKER_NAME = "Имя докладчика не указано"
private const val DEFAULT_JOB_SPEAKER = "Место работы докладчика не указано"

class SpeakerApiDataMapper: Mapper<SpeakerApiData, SpeakerData> {

    override fun map(data: SpeakerApiData?): SpeakerData {
        return SpeakerData(
            fullName = data?.fullName ?: DEFAULT_SPEAKER_NAME,
            job = data?.job ?: DEFAULT_JOB_SPEAKER
        )
    }
}