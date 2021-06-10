package com.uni.inistudents.network

import android.content.Context
import com.uni.inistudents.Preferences
import com.uni.inistudents.network.services.IniStudentService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RestApi @Inject constructor(context: Context, val preferences: Preferences) {

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"
        const val HEADER_CONNECTION = "Connection"
        const val BASE_URL = "http://34.116.254.217"
    }

    private lateinit var retrofit: Retrofit

    lateinit var iniStudentService: IniStudentService

    init {
        invalidate()
    }

    private fun invalidate() {
        initRetrofit()
        initServices()
    }

    private fun initRetrofit() {
//        val loggingInterceptor = HttpLoggingInterceptor { Timber.tag(Consts.LogTag.HTTP).d(it) }
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build()
    }

    private fun initServices() {
        iniStudentService = retrofit.create(IniStudentService::class.java)
    }

//    inner class RequestInterceptor : okhttp3.Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//            TODO("Not yet implemented")
//        }
////        override fun intercept(chain: okhttp3.Interceptor.Chain): Response =
////                chain.proceed(chain.request().newBuilder().apply {
////                    when {
////                        isGuest(preferences) -> {
////                            header(HEADER_AUTHORIZATION, preferences.token)
////                        }
////                        isAuthorized(preferences) -> {
////                            header(HEADER_AUTHORIZATION, "JWT ${preferences.token}")
////                        }
////                    }
////                    header(HEADER_ACCEPT_LANGUAGE, Locale.getDefault().toLanguageTag())
////                    header(HEADER_CONNECTION, "close")
////                }.build())
////    }
//    }
}