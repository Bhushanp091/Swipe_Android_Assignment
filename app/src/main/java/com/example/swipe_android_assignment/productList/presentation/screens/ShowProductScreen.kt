package com.example.swipe_android_assignment.productList.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.swipe_android_assignment.productList.presentation.viewModel.ProductListState
import com.example.swipe_android_assignment.ui.theme.BlueTheme
import com.example.swipe_android_assignment.util.Screen
import com.example.swipe_android_assignment.util.shared_components.CommonProgressBar
import com.example.swipe_android_assignment.util.shared_components.ProductBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowProductScreen(
    productState: ProductListState,
    navController: NavController
) {


    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Home",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },

                actions = {
                    IconButton(
                        onClick = {navController.navigate(Screen.Search.route) },
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                ),

                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddProduct.route) },
                containerColor = BlueTheme,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) { paddingValue ->

        Box(
            modifier = Modifier.padding(paddingValue)
        ) {
            if (productState.isLoading || productState.productList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CommonProgressBar()
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(productState.productList) { productList ->
                        ProductBox(productList = productList)
                    }
                }
            }
        }

    }


}