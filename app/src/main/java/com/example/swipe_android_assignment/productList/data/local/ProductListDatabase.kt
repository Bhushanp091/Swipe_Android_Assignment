package com.example.swipe_android_assignment.productList.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductListEntity::class],
    version = 1
)
abstract class ProductListDatabase:RoomDatabase(){
    abstract val productlistDao: ProductListDao
}