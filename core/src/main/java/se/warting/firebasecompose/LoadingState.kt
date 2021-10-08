package se.warting.firebasecompose

sealed class LoadingState<T> {
    class Loading<T> : LoadingState<T>()
    data class Success<T>(val data: T) : LoadingState<T>()
    data class Failed<T>(val error: Exception) : LoadingState<T>()
}
