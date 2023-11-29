package br.com.lucashsouza.fitnesstracker

import android.app.Application
import br.com.lucashsouza.fitnesstracker.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}