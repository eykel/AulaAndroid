package br.com.aulaandroid

import android.app.Application
import br.com.aulaandroid.di.apiModule
import br.com.aulaandroid.di.aulaAndroidModule
import br.com.aulaandroid.di.dataBaseModule
import br.com.aulaandroid.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(retrofitModule, aulaAndroidModule, apiModule, dataBaseModule)
        }
    }
}