package com.example.swipe_android_assignment.productList.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity
data class ProductListEntity(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double,

    @PrimaryKey val id:String = UUID.randomUUID().toString()
)