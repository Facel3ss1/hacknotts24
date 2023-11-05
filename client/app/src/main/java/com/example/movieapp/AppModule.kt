package com.example.movieapp

import com.example.movieapp.data.remote.api.KtorMovieApiClient
import com.example.movieapp.data.remote.api.MovieApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun providesMovieApi(movieApiImpl: KtorMovieApiClient): MovieApi
}