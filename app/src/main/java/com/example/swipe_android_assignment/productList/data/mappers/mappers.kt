package com.example.swipe_android_assignment.productList.data.mappers

import com.example.swipe_android_assignment.productList.data.local.ProductListEntity
import com.example.swipe_android_assignment.productList.data.remote.dto.ProductDtoItem
import com.example.swipe_android_assignment.productList.domain.model.ProductList

fun ProductListEntity.toProductList():ProductList{
    return ProductList(
        image = image,
        price = price,
        product_name = product_name,
        product_type = product_type,
        tax = tax
    )
}


fun ProductDtoItem.toProductEntity():ProductListEntity{
    return ProductListEntity(
        image = image?: "",
        price = price?: 0.0,
        product_name = product_name?:"",
        product_type = product_type?:"",
        tax = tax?:0.0
    )
}