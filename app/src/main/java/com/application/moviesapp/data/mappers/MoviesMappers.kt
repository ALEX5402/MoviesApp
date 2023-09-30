package com.application.moviesapp.data.mappers

import com.application.moviesapp.data.api.response.MovieDetailsDto
import com.application.moviesapp.data.api.response.MovieFavouriteDto
import com.application.moviesapp.data.api.response.MovieStateDto
import com.application.moviesapp.data.api.response.MovieTrailerDto
import com.application.moviesapp.data.local.entity.MovieNewReleaseEntity
import com.application.moviesapp.data.local.entity.MovieUpcomingEntity
import com.application.moviesapp.data.local.entity.MoviesEntity
import com.application.moviesapp.data.remote.MovieNewReleasesDto
import com.application.moviesapp.data.remote.MovieUpcomingDto
import com.application.moviesapp.data.remote.MoviesDto
import com.application.moviesapp.domain.Movies
import com.application.moviesapp.domain.model.MovieFavourite
import com.application.moviesapp.domain.model.MovieNewRelease
import com.application.moviesapp.domain.model.MovieState
import com.application.moviesapp.domain.model.MovieTrailer
import com.application.moviesapp.domain.model.MovieUpcoming
import com.application.moviesapp.domain.model.MoviesDetail

fun MoviesDto.Result.toMoviesEntity(): MoviesEntity {
    return MoviesEntity(
        adult = adult,
        backdropPath = backdropPath,
        movieId = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount)
}

fun MoviesEntity.toMovies(): Movies {
    return Movies(
        adult = adult,
        backdropPath = backdropPath,
        id = id,
        movieId = movieId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}



fun MovieNewReleasesDto.Result.toMoviesEntity(): MovieNewReleaseEntity {
    return MovieNewReleaseEntity(
        adult = adult,
        backdropPath = backdropPath,
        movieId = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun MovieNewReleaseEntity.toMovies(): MovieNewRelease {
    return MovieNewRelease(
        adult = adult,
        backdropPath = backdropPath,
        id = id,
        movieId = movieId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}


fun MovieUpcomingDto.Result.toMoviesEntity(): MovieUpcomingEntity {
    return MovieUpcomingEntity(
        adult = adult,
        backdropPath = backdropPath,
        movieId = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun MovieUpcomingEntity.toMovies(): MovieUpcoming {
    return MovieUpcoming(
        adult = adult,
        backdropPath = backdropPath,
        id = id,
        movieId = movieId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun MovieTrailerDto.toMovies(): MovieTrailer {
    return MovieTrailer(
        id = id,
        results = results?.filter { it?.site == "YouTube" && it.type == "Trailer" }?.map {
            MovieTrailer.Result(
                id = it?.id,
                isoOne = it?.isoOne,
                isoTwo = it?.isoTwo,
                key = it?.key,
                name = it?.name,
                official = it?.official,
                publishedAt = it?.publishedAt,
                site = it?.site,
                size = it?.size,
                type = it?.type
            )
        }
    )
}

fun MovieFavouriteDto.toMovies(): List<MovieFavourite>? {
    return results?.map {
        MovieFavourite(
            adult = it?.adult,
            backdropPath = it?.backdropPath,
            id = it?.id,
            originalLanguage = it?.originalLanguage,
            originalTitle = it?.originalTitle,
            overview = it?.overview,
            popularity = it?.popularity,
            posterPath = it?.posterPath,
            releaseDate = it?.releaseDate,
            title = it?.title,
            video = it?.video,
            voteAverage = it?.voteAverage,
            voteCount = it?.voteCount
            )
    }
}

fun MovieStateDto.toState(): MovieState {
    return MovieState(favorite = favorite)
}