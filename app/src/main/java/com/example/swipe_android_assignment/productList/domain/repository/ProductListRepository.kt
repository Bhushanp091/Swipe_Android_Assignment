package com.example.swipe_android_assignment.productList.domain.repository

import com.example.swipe_android_assignment.productList.domain.model.ProductList
import com.example.swipe_android_assignment.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response


interface ProductListRepository {

    suspend fun getAllProductList(): Flow<Resource<List<ProductList>>>

    suspend fun addNewProduct(
        productName:String,
        productType:String,
        productPrice:String,
        productTax:String,
        imageFiles: String
    ):Flow<Resource<Response<ProductList>>>

}