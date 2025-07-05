package br.com.aulaandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.aulaandroid.data.model.GithubUserResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: GithubUserResponse) : Long

    @Delete
    suspend fun delete(user: GithubUserResponse) : Int

    @Query("SELECT * FROM User WHERE serverId = :id")
    fun getById(id: Int): Flow<GithubUserResponse>

    @Query("SELECT * FROM User WHERE favorite = 1 AND owner = :owner")
    fun getFavorites(owner: String): Flow<List<GithubUserResponse>>

    @Query("SELECT * FROM User WHERE favorite = 1 AND  owner = :owner")
    suspend fun getStaticFavoriteList(owner: String): List<GithubUserResponse>

    @Query("DELETE FROM User WHERE serverId = :id")
    suspend fun deleteById(id: Int)
}