package hr.ferit.pcbuildlogger

import android.app.Application
import android.content.Context

class PCBuildLoggerApp : Application() {

    companion object {
        lateinit var ApplicationContext : Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        ApplicationContext = this
    }
}