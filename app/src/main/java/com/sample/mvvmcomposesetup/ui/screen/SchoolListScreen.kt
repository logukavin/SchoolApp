package com.sample.mvvmcomposesetup.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.mvvmcomposesetup.R
import com.sample.mvvmcomposesetup.base.UIState
import com.sample.mvvmcomposesetup.networkhelper.NoInternetException
import com.sample.mvvmcomposesetup.remote.ApiConstants
import com.sample.mvvmcomposesetup.ui.base.ShowLoading
import com.sample.mvvmcomposesetup.ui.base.showToast
import com.sample.mvvmcomposesetup.viewmodel.SchoolListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolListScreen : ComponentActivity() {

    private val schoolListViewModel: SchoolListViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Scaffold(topBar = { AppTopAppBar() }) {
                val dataState by schoolListViewModel.schoolListResponse.collectAsState(UIState.Loading)

                when (dataState) {

                    UIState.Loading -> {
                        ShowLoading()
                    }

                    is UIState.Failure -> {
                        val errorText = when ((dataState as UIState.Failure).throwable) {
                            is NoInternetException -> R.string.no_internet_available
                            else -> R.string.something_went_wrong_try_again
                        }
                        showToast(this@SchoolListScreen, getString(errorText))
                    }

                    is UIState.Success -> {
                        (dataState as UIState.Success).data
                        val data = (dataState as UIState.Success).data

                        LazyColumn(
                            modifier = Modifier
                                .padding(
                                    top = 8.dp, bottom = 8.dp, start = 9.dp, end = 9.dp
                                )
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {

                            item {
                                data.forEach {
                                    ListItemView(item = it, onItemClick = { clickedItem ->
                                        val intent = Intent(
                                            this@SchoolListScreen, SchoolDetailsScreen::class.java
                                        ).apply {
                                            putExtra(ApiConstants.DBN, clickedItem.dbn)
                                        }
                                        startActivity(intent)
                                    })


                                }
                            }

                        }

                    }
                }

            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppTopAppBar() {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "School List",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ), modifier = Modifier.fillMaxWidth()
            )
        }
    }
}