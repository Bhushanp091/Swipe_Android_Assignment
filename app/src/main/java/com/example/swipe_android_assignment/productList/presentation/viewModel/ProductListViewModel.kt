package com.example.swipe_android_assignment.productList.presentation.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe_android_assignment.productList.domain.model.ProductList
import com.example.swipe_android_assignment.productList.domain.repository.AddRepository
import com.example.swipe_android_assignment.productList.domain.repository.ProductListRepository
import com.example.swipe_android_assignment.ui.theme.checkInternet
import com.example.swipe_android_assignment.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductListRepository,
    private val addRepository: AddRepository
) : ViewModel() {


    private val _productListState = MutableStateFlow(ProductListState())
    val productListState = _productListState.asStateFlow()
    var productItemList = MutableStateFlow<List<ProductList>>(emptyList())

    private val _isDataPosted = MutableLiveData<Boolean>()
    val isDataPosted: LiveData<Boolean> = _isDataPosted

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error




    private val _pushResponse: MutableLiveData<Response<ProductList>> = MutableLiveData()
    val pushResponse: LiveData<Response<ProductList>> = _pushResponse

    init {
        getAllProductList()
    }

    private fun getAllProductList() {
        viewModelScope.launch {
            _productListState.update {
                it.copy(
                    isLoading = true
                )
            }

            repository.getAllProductList().collectLatest { result ->

                when (result) {
                    is Resource.Error -> {
                        _productListState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message.toString()
                            )
                        }
                    }

                    is Resource.Loading -> {

                        _productListState.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }

                    }

                    is Resource.Success -> {
                        result.data?.let { productList ->
                            _productListState.update {
                                val shuffeldProductlist = productList.shuffled().toMutableList()

                                it.copy(
                                    productList = productListState.value.productList + shuffeldProductlist
                                )
                            }
                            productItemList.value = productList
                        }
                    }
                }

            }

        }


    }


    @OptIn(DelicateCoroutinesApi::class)
    fun addProduct(
        productName: String,
        selectedType: String,
        productPrice: String,
        productTax: String,
        imageFiles: String?
    ) {
        viewModelScope.launch {
            _productListState.update {
                it.copy(
                    isLoading = true
                )
            }
            imageFiles?.let {
                repository.addNewProduct(
                    productName,
                    selectedType,
                    productPrice,
                    productTax,
                    it
                ).collectLatest {result->

                    when(result){
                        is Resource.Error -> {
                            _productListState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message.toString()
                                )
                            }
                        }
                        is Resource.Loading -> {
                            _productListState.update {
                                it.copy(
                                    isLoading = result.isLoading,
                                )
                            }
                        }
                        is Resource.Success -> {

                            result.data?.let {
                                _pushResponse.value = it
                            }
                            _isDataPosted.value = true
                            getAllProductList()
                        }
                    }

                }
            }


        }

    }

    //Searching User
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()
    private val _productLists = productItemList

    @OptIn(FlowPreview::class)
    val products = searchText
        .debounce(1000L)
        .onEach { _isSearching.value = true }
        .combine(_productLists) { searchQuery, userDataList ->
            if (searchQuery.isBlank()) {
                userDataList
            } else {
                // If there is a search query, filter the users based on the query
                kotlinx.coroutines.delay(1000L) //
                userDataList.filter {
                    it.doesMatchwithQuery(searchQuery)
                }
            }
        }
        .onEach {
            _isSearching.value = false
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            productItemList.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }


}