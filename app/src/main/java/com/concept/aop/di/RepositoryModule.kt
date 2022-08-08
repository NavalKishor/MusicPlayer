/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-08-01.
 */
package com.concept.aop.di

import android.content.Context
import com.concept.aop.MyApp
import com.concept.aop.db.MusicDB
import com.concept.aop.repository.RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    // Provide GithubRepository
    single {
        RepositoryImpl(get(),get())
    }
    single {
        MusicDB.getInstance((get<Context>() as MyApp))
    }


}


