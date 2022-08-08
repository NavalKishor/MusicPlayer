/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-07-29.
 */
package com.concept.aop.repository

import androidx.lifecycle.LiveData
import com.concept.aop.db.SongDao
import com.concept.aop.repository.retrofit.Feed


interface Repository {
    val feedLiveData: LiveData<Feed>
    val songDao: SongDao

    suspend fun fetchFeed()
    suspend fun isFeedAvailableInDB():Boolean
    suspend fun addInDB(id:Int,title: String,desc: String, linkSong: String, linkImg: String)


}