/*
 * Copyright (c) 2022, CTL and/or its affiliates. All rights reserved.
 * Created by nkjha on 2022-08-01.
 */
package com.concept.aop.di
import com.concept.aop.db.SongDao
import com.concept.aop.ui.vm.MainViewModel
import com.concept.aop.ui.vm.PlaySongViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module {

    // Provide MainViewModel
    viewModel { MainViewModel(get()) }
    viewModel { PlaySongViewModel() }


}

