package com.concept.aop.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "song")
 data class SongTable(@PrimaryKey(autoGenerate = true) val id: Int,
                      val title: String,
                      val desc: String,
                      val linkSong: String,
                      val linkImg: String)

