package com.example.studyplan

import android.app.Application

/** Holds the app-wide [AppContainer]. Registered via android:name in the manifest. */
class StudyPlanApplication : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
