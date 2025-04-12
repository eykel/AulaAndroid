package br.com.aulaandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.aulaandroid.data.model.GithubUser

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubUser) : Long

    @Delete
    fun delete(user: GithubUser) : Int

    @Query("SELECT * FROM User WHERE id = :id")
    fun getById(id: Int): GithubUser

    @Query("SELECT * FROM User")
    fun getAll(): List<GithubUser>

    @Query("SELECT * FROM User WHERE favorite = 1")
    fun getFavorites(): List<GithubUser>

    @Query("UPDATE User SET favorite = :favorite WHERE id = :id")
    fun setFavorite(id: Int, favorite: Boolean)

    @Query("DELETE FROM User")
    fun deleteAll()

    @Query("DELETE FROM User WHERE id = :id")
    fun deleteById(id: Int)

    @Query("DELETE FROM User WHERE favorite = 1")
    fun deleteFavorites()

}