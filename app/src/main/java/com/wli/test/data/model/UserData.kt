package com.wli.test.data.model

import com.google.gson.annotations.SerializedName

data class UserData(

    @SerializedName("results")
    var result: List<User>? = null,

    @SerializedName("info")
    var info: Info? = null

) : CommonModel()

data class Info (
    val seed: String,
    val results: Long,
    val page: Long,
    val version: String
)