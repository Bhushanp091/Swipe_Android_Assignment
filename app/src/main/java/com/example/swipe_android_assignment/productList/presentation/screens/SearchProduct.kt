package com.example.swipe_android_assignment.productList.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.social_media_app.util.ui_components.SearchTextBox
import com.example.swipe_android_assignment.productList.presentation.viewModel.ProductListState
import com.example.swipe_android_assignment.productList.presentation.viewModel.ProductListViewModel
import com.example.swipe_android_assignment.util.shared_components.CommonProgressBar
import com.example.swipe_android_assignment.util.shared_components.ProductBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductScreen(
    productState: ProductListState,
    navController: NavController,
) {

    val mainViewModel = hiltViewModel<ProductListViewModel>()
    val searchText by mainViewModel.searchText.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                        ) {
                            Icon(
                                Icons.Default.ArrowBackIosNew,
                                contentDescription = "back"
                            )
                        }
                        SearchTextBox(
                            inputText = searchText,
                            label = "Search",
                            onValueChange = { mainViewModel.onSearchTextChange(it) })
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                ),

                )
        },
    ) { paddingValues ->

        val products by mainViewModel.products.collectAsState()
        val isSearching by mainViewModel.isSearching.collectAsState()


        Column(
            modifier = Modifier.padding(paddingValues)
        ) {

            if (isSearching) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CommonProgressBar()
                }
            } else {
                val productLists = products.filter {
                    it.doesMatchwithQuery(searchText)
                }

                if (!productLists.isEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 15.dp)
                    ) {

                        items(products) { productlist ->
                            ProductBox(productlist)
                        }
                    }
                } else {

                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 100.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.ProductionQuantityLimits,
                            contentDescription = null
                        )
                        Text(
                            text = "No Product Found",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.W700,
                            fontSize = 20.sp
                        )
                    }

                }
            }

        }

    }
}