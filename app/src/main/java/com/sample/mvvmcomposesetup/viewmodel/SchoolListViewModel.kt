package com.sample.mvvmcomposesetup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.mvvmcomposesetup.base.UIState
import com.sample.mvvmcomposesetup.dispatcher.DispatcherProvider
import com.sample.mvvmcomposesetup.model.SchoolListResponse
import com.sample.mvvmcomposesetup.networkhelper.NetworkHelper
import com.sample.mvvmcomposesetup.networkhelper.NoInternetException
import com.sample.mvvmcomposesetup.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val appRepo: AppRepository
) : ViewModel() {


    private val _schoolListResponse =
        MutableStateFlow<UIState<List<SchoolListResponse>>>(UIState.Loading)
    val schoolListResponse: StateFlow<UIState<List<SchoolListResponse>>> = _schoolListResponse

    init {
        viewModelScope.launch {
            getSearchList()
        }
    }

    private suspend fun getSearchList() {
        if (!networkHelper.isNetworkConnected()) {
            _schoolListResponse.emit(UIState.Failure(throwable = NoInternetException()))
            return
        }
        _schoolListResponse.emit(UIState.Loading)

        appRepo.getSchoolList().flowOn(dispatcherProvider.io).catch { exception ->
            _schoolListResponse.emit(UIState.Failure(exception))
        }.collect { response ->
            _schoolListResponse.emit(UIState.Success(response))
        }
    }


}