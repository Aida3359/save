package kz.kolesateam.confapp.presentation

sealed class ProgressState {
    object Loading : ProgressState()
    object Done : ProgressState()
}