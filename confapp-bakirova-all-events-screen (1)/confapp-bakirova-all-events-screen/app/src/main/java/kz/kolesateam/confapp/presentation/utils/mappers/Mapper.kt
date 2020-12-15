package kz.kolesateam.confapp.presentation.utils.mappers

interface Mapper<SRC, DST> {
    fun map(data: SRC?): DST
}