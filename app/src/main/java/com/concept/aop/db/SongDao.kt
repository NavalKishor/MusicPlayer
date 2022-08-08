package com.concept.aop.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(song: SongTable)

    @Update
    fun update(song: SongTable)

    @Delete
    fun delete(song: SongTable)

    @Query("delete from song")
    fun deleteAllSongs()

    @Query("select * from song")
    fun getAllSongs(): LiveData<List<SongTable>>
    @Query("select * from song")
    fun getSongs():  List<SongTable>

    @Query("select count(*) from song")
    fun isDataAvailable(): Int
}