package com.example.swipe_android_assignment.productList.presentation.viewModel

import com.example.swipe_android_assignment.productList.domain.model.ProductList

data class ProductListState(
    val isLoading:Boolean = false,

    val productList:List<ProductList> = listOf(),


    val error:String = ""
)
