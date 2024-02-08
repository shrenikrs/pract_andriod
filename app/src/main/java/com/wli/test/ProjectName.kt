package com.wli.test

import android.app.Application
import android.content.Context
import com.wli.test.data.database.SampleDatabase
import com.wli.test.data.network.WebServiceClient
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProjectName : Application() {

    override fun onCreate() {
        super.onCreate()
        WebServiceClient.init(this)
        //FirebaseApp.initializeApp(this)
        //FirebaseMessaging.getInstance().isAutoInitEnabled = true
        instance = applicationContext
        //database = SampleDatabase.getInstance(applicationContext)!!
    }

    companion object {
        /*private var instance: ProjectName? = null

        fun getInstance(): ProjectName? {
            if (instance == null) {
                instance = ProjectName()
            }
            return instance
        }*/
        lateinit var instance: Context
        lateinit var database: SampleDatabase
    }

}