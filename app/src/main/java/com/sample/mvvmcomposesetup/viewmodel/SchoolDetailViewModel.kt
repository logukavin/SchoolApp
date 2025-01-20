package com.sample.mvvmcomposesetup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvmcomposesetup.base.UIState
import com.sample.mvvmcomposesetup.dispatcher.DispatcherProvider
import com.sample.mvvmcomposesetup.model.SchoolDetailsResponse
import com.sample.mvvmcomposesetup.networkhelper.NetworkHelper
import com.sample.mvvmcomposesetup.networkhelper.NoInternetException
import com.sample.mvvmcomposesetup.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolDetailViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val appRepo: AppRepository
) : ViewModel() {

    private val _dbn = MutableStateFlow<String>("")

    private val _schoolDetailsResponse =
        MutableStateFlow<UIState<List<SchoolDetailsResponse>>>(UIState.Loading)
    val schoolDetailsResponse: StateFlow<UIState<List<SchoolDetailsResponse>>> =
        _schoolDetailsResponse


    init {
        viewModelScope.launch {
            delay(2000)
            getSchoolDetails(_dbn.value)
        }
    }

    private suspend fun getSchoolDetails(dbn: String) {
        if (!networkHelper.isNetworkConnected()) {
            _schoolDetailsResponse.emit(UIState.Failure(throwable = NoInternetException()))
            return
        }
        _schoolDetailsResponse.emit(UIState.Loading)

        appRepo.getSchoolDetails(dbn).flowOn(dispatcherProvider.io).catch { exception ->
            _schoolDetailsResponse.emit(UIState.Failure(exception))
        }.collect { response ->
            _schoolDetailsResponse.emit(UIState.Success(response))
        }
    }

    fun updateDbn(dbn: String) {
        _dbn.value = dbn


    }
}