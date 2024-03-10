package com.example.swipe_android_assignment.productList.data.repository

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.example.swipe_android_assignment.productList.data.local.ProductListDatabase
import com.example.swipe_android_assignment.productList.data.mappers.toProductEntity
import com.example.swipe_android_assignment.productList.data.mappers.toProductList
import com.example.swipe_android_assignment.productList.data.remote.ApiService
import com.example.swipe_android_assignment.productList.domain.model.ProductList
import com.example.swipe_android_assignment.productList.domain.repository.ProductListRepository
import com.example.swipe_android_assignment.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val database: ProductListDatabase
) : ProductListRepository {


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getAllProductList(): Flow<Resource<List<ProductList>>> {
        return flow {
            emit(Resource.Loading(true))

            //getting movies from database if available
            val productsFromRoomDb = database.productlistDao.getAllProducts()

            if (productsFromRoomDb.isNotEmpty()) {
                emit(Resource.Success(productsFromRoomDb.map { productListEntity ->
                    productListEntity.toProductList()
                }))
                emit(Resource.Loading(false))
            }

            //Room Database is empty getting products from api
            val productFromApi = try {
                api.getProductList()
            } catch (e: Exception) {
                Log.d("tag", "Error Loading Products1")
                emit(Resource.Error("Error Loading Products $e"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                Log.d("tag", "Error Loading Products")
                emit(Resource.Error("Error Loading Products2 $e"))
                return@flow
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("tag", "Error Loading Products")
                emit(Resource.Error("Error Loading Products3 $e"))
                return@flow
            }

            //Storing data in database first them retrieving from database
            val productList = productFromApi.let { prductList ->
                prductList.map {
                    it.toProductEntity()
                }

            }

            database.productlistDao.upsertProduct(productList)

            emit(Resource.Success(productList.map {
                it.toProductList()
            }))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun addNewProduct(
        productName: String,
        productType: String,
        productPrice: String,
        productTax: String,
        imageFiles: String
    ): Flow<Resource<Response<ProductList>>> {
        return flow {
            emit(Resource.Loading(true))

           val result =  try {
                api.addProduct(
                    productPrice,
                    productName,
                    productType,
                    productTax,
                    imageFiles
                )
            } catch (e: Exception) {
                Log.d("tag", e.toString())
               emit(Resource.Error("$e"))
                throw e

            }

            if (result.isSuccessful){
                emit(Resource.Success(result))
            }else{
                emit(Resource.Error("Check Your Internet"))
            }

            emit(Resource.Loading(false))
        }

    }

}