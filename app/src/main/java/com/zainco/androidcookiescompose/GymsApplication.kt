package com.zainco.androidcookiescompose

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GymsApplication: Application() {
    init {
        application = this
    }
    companion object{
        private lateinit var application: GymsApplication
        fun getAppContext(): Context = application.applicationContext
    }
}