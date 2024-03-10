package com.example.swipe_android_assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swipe_android_assignment.productList.presentation.screens.AddProductScreen
import com.example.swipe_android_assignment.productList.presentation.screens.SearchProductScreen
import com.example.swipe_android_assignment.productList.presentation.screens.ShowProductScreen
import com.example.swipe_android_assignment.productList.presentation.viewModel.ProductListViewModel
import com.example.swipe_android_assignment.ui.theme.Swipe_Android_AssignmentTheme
import com.example.swipe_android_assignment.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Swipe_Android_AssignmentTheme {
                SetBarColor(MaterialTheme.colorScheme.inverseOnSurface)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel = hiltViewModel<ProductListViewModel>()
                    val productState = viewModel.productListState.collectAsState().value
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){
                        composable(Screen.HomeScreen.route){
                            ShowProductScreen(productState = productState,navController)
                        }

                        composable(Screen.Search.route){
                            SearchProductScreen(productState = productState,navController)
                        }

                        composable(Screen.AddProduct.route){
                            AddProductScreen(productState = productState,navController)
                        }
                    }

                    val result  = remember {
                        mutableStateOf("")
                    }
                }
            }
        }
    }
}

@Composable
private fun SetBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = color) {
        systemUiController.setSystemBarsColor(color)
    }
}
