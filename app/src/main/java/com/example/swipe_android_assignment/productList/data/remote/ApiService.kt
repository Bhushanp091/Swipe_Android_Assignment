package com.example.swipe_android_assignment.productList.data.remote

import com.example.swipe_android_assignment.productList.data.remote.dto.ProductDtoItem
import com.example.swipe_android_assignment.productList.domain.model.ProductList
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @GET("get")
    suspend fun getProductList(): List<ProductDtoItem>

    @FormUrlEncoded
    @POST("add")
    suspend fun addProduct(
        @Field("price")price:String,
        @Field("product_name")product_name:String,
        @Field("product_type")product_type:String,
        @Field("tax")tax:String,
        @Field("image")image:String,
    ): Response<ProductList>

    companion object {
        const val BASE_URL = "https://app.getswipe.in/api/public/"
    }
}