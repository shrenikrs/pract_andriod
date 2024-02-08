package com.wli.test.data.model

import com.google.gson.annotations.SerializedName

/**
 * This class will be used to handle common member properties which we are getting in web call response
 */
open class CommonModel {

        @SerializedName("status")
        var status: String? = null

        @SerializedName("message")
        var message: String? = null


}