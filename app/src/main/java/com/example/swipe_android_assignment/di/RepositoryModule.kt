package com.example.swipe_android_assignment.di

import com.example.swipe_android_assignment.productList.data.repository.ProductListRepositoryImpl
import com.example.swipe_android_assignment.productList.domain.repository.ProductListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindeproductlistRepository(
        productListRepositoryImpl: ProductListRepositoryImpl
    ): ProductListRepository

}