/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-07-29.
 */
package com.concept.aop.repository

import androidx.lifecycle.MutableLiveData
import com.concept.aop.db.SongDao
import com.concept.aop.db.SongTable
import com.concept.aop.repository.retrofit.Feed
import com.concept.aop.repository.retrofit.WebService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.xmlpull.mxp1.MXParser


class RepositoryImpl constructor(
    private val webService: WebService,
    override val songDao: SongDao
): Repository {

//    private val wordDao: SongDao
    override val feedLiveData: MutableLiveData<Feed> = MutableLiveData()

    override suspend fun fetchFeed() {
        val feed = try {
            webService.getFeed()
        } catch (cause: Throwable) {
            cause.printStackTrace()
            throw cause
        }
        return feedLiveData.postValue(feed)
    }

    override suspend fun isFeedAvailableInDB(): Boolean {
        return songDao.isDataAvailable().also { println("data count:$it") }>0
    }

    override suspend fun addInDB(id:Int,title: String, desc: String, linkSong: String, linkImg: String) {
        songDao.insert(SongTable( id, title, desc, linkSong, linkImg))
    }

    suspend fun getFeedAsync(): Deferred<Feed?> {
        return withContext(Dispatchers.IO) {
            async {
                try {
                    // for demo purpose, hence no error checking
                    webService.getFeedAsync().await().body() as Feed
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}