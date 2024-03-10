package com.example.swipe_android_assignment.di

import android.app.Application
import androidx.room.Room
import com.example.swipe_android_assignment.productList.data.local.ProductListDatabase
import com.example.swipe_android_assignment.productList.data.remote.ApiService
import com.example.swipe_android_assignment.productList.domain.repository.AddRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()



    @Provides
    @Singleton
    fun providesProductApi() : ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiService.BASE_URL)
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesProudctDatabase(app: Application):ProductListDatabase {
        return Room.databaseBuilder(
            app,
            ProductListDatabase::class.java,
            "productList.db"
        ).build()
    }

    @Provides
    fun providesAddRepository(apiService: ApiService): AddRepository {
        return AddRepository(apiService)
    }
}