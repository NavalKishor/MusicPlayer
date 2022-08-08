/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-08-01.
 */
package com.concept.aop

import android.app.Application
import com.concept.aop.db.MusicDB
import com.concept.aop.di.appModule
import com.concept.aop.di.networkModule
import com.concept.aop.di.repositoryModule
import com.concept.aop.di.vmModule
import com.concept.aop.repository.RepositoryImpl
import com.concept.aop.repository.retrofit.WebService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class MyApp  : Application() {

//    val database by lazy { MusicDB.getInstance(this) }
//    val webService by lazy { WebService.create() }
//    val repository by lazy { RepositoryImpl(webService,database.songDao()) }


    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(
            appModule,
//        repositoryModule,
        networkModule
//        vmModule
        )
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModules)
        }
    }

}