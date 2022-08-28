package khamdamov.dilmurod.a7minsworkout

import android.app.Application

class WorkOutApp: Application() {

    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}