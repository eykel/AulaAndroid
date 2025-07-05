package br.com.aulaandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.aulaandroid.data.local.dao.UserDAO
import br.com.aulaandroid.data.model.GithubUserResponse

@Database(entities = [GithubUserResponse::class], version = 6)
abstract class GitUserDataBase : RoomDatabase(){

    abstract fun userDao(): UserDAO

    companion object {
        const val DATABASE_NAME = "github_user_db"

        @Volatile
        private lateinit var INSTANCE: GitUserDataBase

        fun getDatabase(context: Context): GitUserDataBase {
            if (!::INSTANCE.isInitialized) {
                 synchronized(this) {
                     INSTANCE = Room.databaseBuilder(
                         context.applicationContext,
                         GitUserDataBase::class.java,
                         DATABASE_NAME
                     ).build()
                 }
            }
            return INSTANCE
        }
    }
}