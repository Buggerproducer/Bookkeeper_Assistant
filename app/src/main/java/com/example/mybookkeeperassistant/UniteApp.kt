package com.example.mybookkeeperassistant

import android.app.Application
import database.ManageDB

class UniteApp :Application(){
    override fun onCreate() {
        super.onCreate()
        ManageDB.initializeDB(applicationContext)

    }
}