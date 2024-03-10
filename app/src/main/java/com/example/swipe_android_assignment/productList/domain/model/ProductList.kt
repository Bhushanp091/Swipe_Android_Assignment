package com.example.swipe_android_assignment.productList.domain.model

data class ProductList(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
){
    fun doesMatchwithQuery(query:String):Boolean{
        val matchingCombinations = listOf(
            product_name,
            "$product_name $product_name",
            "${product_name.first()}",
        )

        return matchingCombinations.any {
            it.contains(query, ignoreCase = true,)
        }
    }
}
