package com.rafal.marvelcomics.di

import com.rafal.marvelcomics.model.MarvelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val PUBLIC_KEY = "ce8155a67eab26e1f8c4fcab014a4118"
const val PRIVATE_KEY = "b0c4e3ee66d7cdbe0413ee0ef579d4c4703b2d3a"
private const val BASE_URL = "https://gateway.marvel.com/v1/public/"

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    var request = chain.request()
                    val url =
                        request.url.newBuilder().addQueryParameter("apikey", PUBLIC_KEY).build()
                    request = request.newBuilder().url(url).build()
                    chain.proceed(request)
                }
            )
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MarvelAPI {
        return retrofit.create(MarvelAPI::class.java)
    }
}