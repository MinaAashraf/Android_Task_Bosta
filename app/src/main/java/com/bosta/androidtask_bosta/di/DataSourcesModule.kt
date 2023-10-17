package com.bosta.androidtask_bosta.di

import com.bosta.androidtask_bosta.data.remote.profile.ProfileRemoteDataSource
import com.bosta.androidtask_bosta.data.remote.profile.ProfileRemoteDataSourceImpl
import com.bosta.androidtask_bosta.data.repository.ProfileRepositoryImpl
import com.bosta.androidtask_bosta.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourcesModule {
    @Singleton
    @Binds
    abstract fun provideProfileRemoteDataSource(profileRemoteDataSourceImpl: ProfileRemoteDataSourceImpl): ProfileRemoteDataSource


    @Singleton
    @Binds
    abstract fun provideProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository


}