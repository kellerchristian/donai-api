package com.donai.api.di

import org.koin.dsl.module

val appModule = module {
    includes(
        requestModule,
        commitmentModule
    )
}