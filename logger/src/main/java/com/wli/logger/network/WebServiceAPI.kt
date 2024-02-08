package com.wli.logger.network


import com.wli.logger.database.entity.Logs
import retrofit2.Call
import retrofit2.http.GET


interface WebServiceAPI {

    @GET("Demonuts/JsonTest/Tennis/json_parsing.php")
    fun getTennisPlayers(): Call<Logs>

}