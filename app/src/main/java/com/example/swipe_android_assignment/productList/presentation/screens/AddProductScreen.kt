package com.example.swipe_android_assignment.productList.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toFile
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.social_media_app.util.ui_components.TextBox
import com.example.social_media_app.util.ui_components.TextBoxNumber
import com.example.swipe_android_assignment.productList.domain.model.ProductList
import com.example.swipe_android_assignment.productList.presentation.viewModel.ProductListState
import com.example.swipe_android_assignment.productList.presentation.viewModel.ProductListViewModel
import com.example.swipe_android_assignment.ui.theme.BlueTheme
import com.example.swipe_android_assignment.ui.theme.checkInternet
import com.example.swipe_android_assignment.util.Screen
import com.example.swipe_android_assignment.util.shared_components.CommonProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    productState: ProductListState,
    navController: NavController,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AddProduct",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },

                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(end = 10.dp)
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                ),

                )
        },
    ) { paddingValues ->
        val context = LocalContext.current

        val productName = remember { mutableStateOf("") }
        val productPrice = remember { mutableStateOf("") }
        val productTax = remember { mutableStateOf("") }
        var expanded = remember { mutableStateOf(false) }

        val productTypeList = listOf("Product", "Service", "Yt", "NewType", "None")
        var selectedType by remember { mutableStateOf("") }

        var imageUrl = remember { mutableStateOf<Uri?>(null) }
        var isPosting = remember { mutableStateOf(false) }

        val mainViewModel = hiltViewModel<ProductListViewModel>()
        val isDataPosted by mainViewModel.isDataPosted.observeAsState(false)
        val data = mainViewModel.pushResponse.observeAsState(null)
        val checkInternet = remember {
            mutableStateOf(checkInternet(context ))
        }


        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
                imageUri?.let {
                    imageUrl.value = it
                }
            }


        LaunchedEffect(isDataPosted) {
            if (isDataPosted) {
                Toast.makeText(context, "Product Added Successfully", Toast.LENGTH_LONG)
                    .show()
                navController.navigate(Screen.HomeScreen.route)
            }
            productName.value = ""
            productPrice.value = ""
            productTax.value = ""
            imageUrl.value = null
            isPosting.value = false
        }



        if (productState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CommonProgressBar()
            }
        }else {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                TextBox(
                    inputText = productName.value,
                    label = "Product Name",
                    onValueChange = { productName.value = it })
                TextBoxNumber(
                    inputText = productPrice.value,
                    label = "Product Price",
                    onValueChange = { productPrice.value = it })
                TextBoxNumber(
                    inputText = productTax.value,
                    label = "Product Tax",
                    onValueChange = { productTax.value = it })

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    OutlinedButton(
                        onClick = { expanded.value = !expanded.value },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .size(48.dp),
                        shape = RoundedCornerShape(30),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text(text = "Select Type $selectedType")

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 150.dp),
                    ) {
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            offset = DpOffset(10.dp, 10.dp),
                            modifier = Modifier.align(Alignment.Center)
                        ) {

                            productTypeList.forEach { category ->

                                DropdownMenuItem(
                                    text = { Text(text = category) },
                                    onClick = {

                                        selectedType = category
                                        if (selectedType == "None") selectedType = ""
                                        expanded.value = false
                                    },

                                    )

                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.padding(16.dp))

                Box(
                    modifier = Modifier.background(Color.LightGray),
                    contentAlignment = Alignment.TopEnd
                ) {
                    if (imageUrl.value != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = imageUrl.value),
                            contentDescription = "post Image",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RectangleShape),
                            contentScale = ContentScale.Crop
                        )
                        Box() {
                            IconButton(onClick = { imageUrl.value = null }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.padding(10.dp))
                IconButton(onClick = {
                    galleryLauncher.launch("image/*")
                }, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null,
                            tint = BlueTheme,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(text = "Add Image", color = BlueTheme)

                    }

                }
                Spacer(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OutlinedButton(
                        onClick = { navController.navigate(Screen.HomeScreen.route) },
                    ) {
                        Text(text = "Cancel")
                    }
                    OutlinedButton(
                        onClick = {
                            if (!checkInternet.value) {
                                Toast.makeText(
                                    context,
                                    "Check Your Internet Connection",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                if (productPrice.value.isDigitsOnly() && productTax.value.isDigitsOnly()) {
                                    if (
                                        productName.value.isNotEmpty() &&
                                        selectedType.isNotEmpty() &&
                                        productPrice.value.isNotEmpty() &&
                                        productTax.value.isNotEmpty()
                                    ) {
                                        mainViewModel.addProduct(
                                            productName.value,
                                            selectedType,
                                            productPrice.value,
                                            productTax.value,
                                            imageUrl.value.toString()
                                        )
                                    } else Toast.makeText(
                                        context,
                                        "Please Fill All Details",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else Toast.makeText(
                                    context,
                                    "Price and Tax should be in Digit Only",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                    ) {
                        Text(text = "Post")
                    }
                }

            }
        }
    }


}