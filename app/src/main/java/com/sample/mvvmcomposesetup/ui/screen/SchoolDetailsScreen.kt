package com.sample.mvvmcomposesetup.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sample.mvvmcomposesetup.R
import com.sample.mvvmcomposesetup.base.UIState
import com.sample.mvvmcomposesetup.networkhelper.NoInternetException
import com.sample.mvvmcomposesetup.remote.ApiConstants
import com.sample.mvvmcomposesetup.ui.base.ShowLoading
import com.sample.mvvmcomposesetup.ui.base.showToast
import com.sample.mvvmcomposesetup.viewmodel.SchoolDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolDetailsScreen : ComponentActivity() {

    private val schoolDetailViewModel: SchoolDetailViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val dbn = intent.getStringExtra(ApiConstants.DBN) ?: ""

            schoolDetailViewModel.updateDbn(dbn)
            Scaffold(topBar = { AppTopAppBar() }) {
                val dataState by schoolDetailViewModel.schoolDetailsResponse.collectAsState(UIState.Loading)

                when (dataState) {

                    UIState.Loading -> {
                        ShowLoading()
                    }

                    is UIState.Failure -> {
                        val errorText = when ((dataState as UIState.Failure).throwable) {
                            is NoInternetException -> R.string.no_internet_available
                            else -> R.string.something_went_wrong_try_again
                        }
                        showToast(this@SchoolDetailsScreen, getString(errorText))
                    }

                    is UIState.Success -> {
                        (dataState as UIState.Success).data


                        val data = (dataState as UIState.Success).data



                        data.forEach {
                            Column(

                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()

                            ) {


                                Row(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterHorizontally)
                                ) {
                                    Text(
                                        text = it.school_name.toString(),
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        style = typography.labelSmall,
                                        modifier = Modifier.absolutePadding(
                                            left = 10.dp, right = 10.dp, top = 10.dp, bottom = 0.dp
                                        )
                                    )
                                }


                                Column(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterHorizontally)
                                ) {
                                    Text(
                                        text = it.sat_math_avg_score.toString(),
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        style = typography.labelSmall,
                                        modifier = Modifier.absolutePadding(
                                            left = 10.dp, right = 10.dp, top = 10.dp, bottom = 0.dp
                                        )
                                    )

                                    Text(
                                        text = it.sat_writing_avg_score.toString(),
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        style = typography.labelSmall,
                                        modifier = Modifier.absolutePadding(
                                            left = 10.dp, right = 10.dp, top = 10.dp, bottom = 0.dp
                                        )
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .align(alignment = Alignment.CenterHorizontally)
                                ) {
                                    Text(
                                        text = it.sat_critical_reading_avg_score.toString(),
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        style = typography.labelSmall,
                                        modifier = Modifier.absolutePadding(
                                            left = 10.dp, right = 10.dp, top = 10.dp, bottom = 0.dp
                                        )
                                    )
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
                            text = "School Details",
                            modifier = Modifier.align(Alignment.Center),
                            style = typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary)
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