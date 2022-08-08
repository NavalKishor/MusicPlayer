package com.concept.aop.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [SongTable::class], version = 1, exportSchema = true)
abstract class MusicDB : RoomDatabase() {
    abstract fun songDao(): SongDao
    companion object {
        private lateinit var instance: MusicDB
        private const val dbName="MusicDb"

        @Synchronized
        fun getInstance(ctx: Context): MusicDB {
            if(!::instance.isInitialized )
                instance = Room.databaseBuilder(ctx.applicationContext,
                    MusicDB::class.java, dbName)
                    .fallbackToDestructiveMigration() //migration not handled
                    .addCallback(roomCallback)
                    .build()
            return instance
        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // if prepoulate data or only 1 time event in app life
            }
        }


    }



}