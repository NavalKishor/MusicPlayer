package com.concept.aop.di

import android.content.Context
import com.concept.aop.MyApp
import com.concept.aop.repository.retrofit.WebService
import com.concept.aop.repository.retrofit.xmlparser.XStreamXmlConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.io.File

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import com.thoughtworks.xstream.io.xml.StaxDriver

import okhttp3.logging.HttpLoggingInterceptor


val networkModule = module {

    // Provide Gson
    single<Gson> {
        GsonBuilder().create()
    }

    // Provide HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Provide OkHttpClient
    single {
        val cacheDir = File((get<Context>() as MyApp).cacheDir, "http")
        val cache = Cache(
            cacheDir,
            10 * 1024 * 1024 // 10 MB
        )

        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }


    // Provide WebService
    single {
        DomDriver()
    }
    single {
        StaxDriver()
    }

   /* single {
        XStream(get<DomDriver>()).apply {
            autodetectAnnotations(true)
        }
    }*/
    single {
        WebService.provideXStream().apply {
            autodetectAnnotations(true)
        }
    }
  /*  single {
        XStream(get<StaxDriver>()).apply {
            autodetectAnnotations(true)
        }
    }*/

    single {
        XStreamXmlConverterFactory.create(get<XStream>())
    }


// Provide WebService
    single<WebService> {
        WebService.create(get(),get());
    }

}

