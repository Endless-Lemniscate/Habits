package com.example.habits

import android.app.Application
import com.example.habits.di.components.ApplicationComponent
import com.example.habits.di.components.DaggerApplicationComponent
import com.example.habits.di.modules.ContextModule


class HabitsApplication: Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()

    }
}