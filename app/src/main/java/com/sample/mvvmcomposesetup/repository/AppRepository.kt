package com.sample.mvvmcomposesetup.repository


import com.sample.mvvmcomposesetup.model.SchoolDetailsResponse
import com.sample.mvvmcomposesetup.model.SchoolListResponse
import com.sample.mvvmcomposesetup.remote.ApiDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepository @Inject constructor(private val apiDataSource: ApiDataSource) {

    suspend fun getSchoolList(): Flow<List<SchoolListResponse>> = flow {
        emit(apiDataSource.getSchoolList())
    }

    suspend fun getSchoolDetails(dbn: String): Flow<List<SchoolDetailsResponse>> = flow {
        emit(apiDataSource.getSchoolDetails(dbn))
    }

}
