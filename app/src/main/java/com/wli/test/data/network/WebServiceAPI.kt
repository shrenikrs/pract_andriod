package com.wli.test.data.network

import com.wli.test.data.model.UserData
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServiceAPI {

    @GET("https://randomuser.me/api")
    suspend fun getUserList(@Query("results") count: Int, @Query("inc") inc: String): UserData

}