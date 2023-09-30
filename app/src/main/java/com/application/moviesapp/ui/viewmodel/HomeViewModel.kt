package com.application.moviesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.application.moviesapp.data.api.response.MovieNewReleasesResponse
import com.application.moviesapp.data.api.response.MovieTopRatedResponse
import com.application.moviesapp.data.remote.MovieNewReleasesDto
import com.application.moviesapp.data.repository.AuthRepository
import com.application.moviesapp.data.repository.MoviesRepository
import com.application.moviesapp.domain.MoviesNewReleaseUseCase
import com.application.moviesapp.domain.MoviesUseCase
import com.application.moviesapp.domain.MoviesWithNewReleases
import com.application.moviesapp.domain.usecase.MovieUpdateFavouriteInteractor
import com.application.moviesapp.domain.usecase.MoviesUpcomingUseCase
import com.application.moviesapp.ui.signin.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

sealed interface MoviesWithNewReleaseUiState {
    object Loading: MoviesWithNewReleaseUiState
    data class Success(val moviesWithNewReleases: MoviesWithNewReleases): MoviesWithNewReleaseUiState
    object Failure: MoviesWithNewReleaseUiState
}
sealed interface MovieNewReleaseUiState {
    object Loading: MovieNewReleaseUiState
    data class Success(val moviesNewReleases: MovieNewReleasesDto): MovieNewReleaseUiState
    object Failure: MovieNewReleaseUiState
}
sealed interface MovieTopRatedUiState {
    object Loading: MovieTopRatedUiState
    data class Success(val movieTopRated: MovieTopRatedResponse): MovieTopRatedUiState
    object Failure: MovieTopRatedUiState
}
@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: MoviesUseCase,
                                        private val moviesRepository: MoviesRepository,
                                        private val moviesNewReleaseUseCase: MoviesNewReleaseUseCase,
                                        private val moviesUpcomingUseCase: MoviesUpcomingUseCase,
                                        private val authRepository: AuthRepository,


    ): ViewModel() {

    private companion object {
        const val TAG = "HomeViewModel"
    }

    private var _moviesWithNewReleaseUiState = MutableStateFlow<MoviesWithNewReleaseUiState>(MoviesWithNewReleaseUiState.Loading)
    val moviesWithNewReleaseUiState: StateFlow<MoviesWithNewReleaseUiState> = _moviesWithNewReleaseUiState

    private var _moviesNewReleaseUiState = MutableStateFlow<MovieNewReleaseUiState>(MovieNewReleaseUiState.Loading)
    val moviesNewReleaseUiState: StateFlow<MovieNewReleaseUiState> = _moviesNewReleaseUiState

    private var _moviesTopRatedUiState = MutableStateFlow<MovieTopRatedUiState>(MovieTopRatedUiState.Loading)
    val movieTopRatedUiState: StateFlow<MovieTopRatedUiState> = _moviesTopRatedUiState

    private val auth = Firebase.auth

    private var _profileInfoUiState = MutableStateFlow<UserData>(getSignedInUser() ?: UserData(userId = "", userName = "", profilePictureUrl = "", email = ""))
    val profileInfoUiState: StateFlow<UserData> = _profileInfoUiState

    fun getMoviesWithNewReleases() = viewModelScope.launch(Dispatchers.IO) {
        _moviesWithNewReleaseUiState.value = MoviesWithNewReleaseUiState.Loading

        try {
            val result = useCase.invoke()
            _moviesWithNewReleaseUiState.value = MoviesWithNewReleaseUiState.Success(result)
            Timber.tag(TAG).d(result.toString())
        } catch (exception: IOException) {
            _moviesWithNewReleaseUiState.value = MoviesWithNewReleaseUiState.Failure
            Timber.tag(TAG).e(exception)
        }
    }
 
    fun getMovieNewReleases() = viewModelScope.launch(Dispatchers.IO) {
        _moviesNewReleaseUiState.value = MovieNewReleaseUiState.Loading

        try {
            val result = moviesRepository.getNewReleasesList()
            _moviesNewReleaseUiState.value = MovieNewReleaseUiState.Success(result)
            Timber.tag(TAG).d(result.toString())
        } catch (exception: IOException) {
            _moviesNewReleaseUiState.value = MovieNewReleaseUiState.Failure
            Timber.tag(TAG).e(exception)
        }
    }

    fun getMoviesTopRated() = viewModelScope.launch {
        _moviesTopRatedUiState.value = MovieTopRatedUiState.Loading

        try {
            val result = moviesRepository.getMoviesTopRated()
            _moviesTopRatedUiState.value = MovieTopRatedUiState.Success(result)
            Timber.tag(TAG).d(result.toString())
        } catch (exception: IOException) {
            _moviesTopRatedUiState.value = MovieTopRatedUiState.Failure
            Timber.tag(TAG).e(exception)
        }
    }

    fun moviesNewReleasePagingFlow() = moviesNewReleaseUseCase.invoke().cachedIn(viewModelScope)

    fun moviesUpcomingPagingFlow() = moviesUpcomingUseCase.invoke().cachedIn(viewModelScope)


    private fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName,
            profilePictureUrl = photoUrl.toString(),
            email = email
            )
    }

    fun signOut() = viewModelScope.launch {
        authRepository.signOut()
    }



}