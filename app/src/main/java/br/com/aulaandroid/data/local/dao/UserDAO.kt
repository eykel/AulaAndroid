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

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Int): Flow<GithubUserResponse>

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<GithubUserResponse>

    @Query("SELECT * FROM User WHERE favorite = 1")
    fun getFavorites(): Flow<List<GithubUserResponse>>

    @Query("SELECT * FROM User WHERE favorite = 1")
    suspend fun getStaticFavoriteList(): List<GithubUserResponse>

    @Query("UPDATE User SET favorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: Int, favorite: Boolean)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

    @Query("DELETE FROM User WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM User WHERE favorite = 1")
    suspend fun deleteFavorites()

}