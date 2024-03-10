package com.example.swipe_android_assignment.productList.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface ProductListDao{


    @Upsert
    suspend fun upsertProduct(product:List<ProductListEntity>)


    @Query("SELECT * FROM productlistentity")
    suspend fun getAllProducts():List<ProductListEntity>

}