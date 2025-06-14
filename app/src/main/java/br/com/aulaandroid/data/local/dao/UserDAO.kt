package br.com.aulaandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.aulaandroid.data.model.GithubUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: GithubUser) : Long

    @Delete
    suspend fun delete(user: GithubUser) : Int

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Int): Flow<GithubUser>

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<GithubUser>

    @Query("SELECT * FROM User WHERE favorite = 1")
    fun getFavorites(): Flow<List<GithubUser>>

    @Query("SELECT * FROM User WHERE favorite = 1")
    suspend fun getStaticFavoriteList(): List<GithubUser>

    @Query("UPDATE User SET favorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: Int, favorite: Boolean)

    @Query("DELETE FROM User")
    suspend fun deleteAll()

    @Query("DELETE FROM User WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM User WHERE favorite = 1")
    suspend fun deleteFavorites()

}