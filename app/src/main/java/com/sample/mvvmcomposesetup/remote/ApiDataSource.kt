package com.sample.mvvmcomposesetup.remote

import com.sample.mvvmcomposesetup.model.SchoolDetailsResponse
import com.sample.mvvmcomposesetup.model.SchoolListResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiDataSource {

    @GET(ApiConstants.API_SCHOOL_LIST)
    suspend fun getSchoolList(): List<SchoolListResponse>

    @GET(ApiConstants.API_SCHOOL_DETAILS)
    suspend fun getSchoolDetails(@Query(ApiConstants.DBN) dbn: String): List<SchoolDetailsResponse>
}
