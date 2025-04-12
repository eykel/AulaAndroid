package br.com.aulaandroid.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.aulaandroid.data.local.dao.UserDAO
import br.com.aulaandroid.data.model.GithubUser

@Database(entities = [GithubUser::class], version = 1)
abstract class GitUserDataBase : RoomDatabase(){

    abstract fun userDao(): UserDAO

    companion object {
        private lateinit var INSTANCE: GitUserDataBase

        fun getDatabase(context: Context): GitUserDataBase {
            if (!::INSTANCE.isInitialized) {
                 //DATA BASE SERA CRIADO AQUI
            }
            return INSTANCE
        }
    }
}