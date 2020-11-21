package com.example.habits.di.modules

import com.example.data.network.DoubletappApiService
import com.example.data.repository.RemoteHabitRepositoryImpl
import com.example.domain.repository.RemoteHabitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RemoteHabitRepositoryModule {
    @Provides
    fun provideRemoteHabitRepository(api: DoubletappApiService): RemoteHabitRepository {
        return RemoteHabitRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideApiService(): DoubletappApiService {
        return DoubletappApiService.get()
    }
}