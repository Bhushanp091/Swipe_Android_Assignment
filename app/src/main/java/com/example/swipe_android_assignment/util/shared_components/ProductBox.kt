package com.example.swipe_android_assignment.util.shared_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.swipe_android_assignment.productList.domain.model.ProductList
import java.math.RoundingMode
import java.text.SimpleDateFormat
import kotlin.math.absoluteValue

@Composable
fun ProductBox(
    productList: ProductList
) {

    val context = LocalContext.current
    val mainImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(productList.image).size(Size.ORIGINAL).build()
    ).state


    Card(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)

    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            if (mainImageState is AsyncImagePainter.State.Loading) {
                CommonProgressBar()
            }

            if (mainImageState is AsyncImagePainter.State.Error) {
                Icon(
                    imageVector = Icons.Default.ImageNotSupported,
                    contentDescription = "error",
                    tint = Color.Gray,
                    modifier = Modifier.size(70.dp)
                )
            }

            if (mainImageState is AsyncImagePainter.State.Success) {
                Image(
                    painter = mainImageState.painter,
                    contentDescription = "ProductImage",
                    contentScale = ContentScale.Crop
                )
            }


        }
        Column (
            modifier = Modifier  .padding(start = 16.dp, end = 8.dp)
                .padding(top = 10.dp)
        ){
            Text(
                text = productList.product_name,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
            )

            Text(
                text = productList.product_type,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier.padding(top = 10.dp, bottom = 16.dp).padding(5.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val roundedNumber = "%.2f".format(productList.price).toDouble()
                val roundedNumberTax = "%.2f".format(productList.tax).toDouble()
                Text(
                    text = "$ $roundedNumber",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light,
                )
                Text(
                    text = "tax: $roundedNumberTax",
                    fontSize = 13.sp,
                )
            }
        }

    }
}