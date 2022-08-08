/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-08-01.
 */
package com.concept.aop.di


import android.content.Context
import com.concept.aop.MyApp
import com.concept.aop.db.MusicDB
import com.concept.aop.repository.Repository
import com.concept.aop.repository.RepositoryImpl
import com.concept.aop.repository.retrofit.WebService
import com.concept.aop.ui.vm.HomeViewModel
import com.concept.aop.ui.vm.MainViewModel
import com.concept.aop.ui.vm.PlaySongViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Classical DSL
val appModule = module {
    // single instance of HelloRepository

    single { MusicDB.getInstance((get<Context>() as MyApp)) }

    factory { get<MusicDB>().songDao() }



    // Provide WebService
//    single {
//        WebService.create(get<OkHttpClient>());
//    }

    single<Repository> { RepositoryImpl(get(),get()) }
    // Simple MainViewModel Factory
//    factory { MainViewModel(get()) }
//    factory { PlaySongViewModel() }
//    factory { ViewModelProvider(this).get(PlaySongViewModel::class.java) }
    // MyViewModel ViewModel
    viewModel { MainViewModel(get()) }
    viewModel { PlaySongViewModel() }
    viewModel { HomeViewModel() }

}
