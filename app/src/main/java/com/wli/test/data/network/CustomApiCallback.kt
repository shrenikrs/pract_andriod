package com.wli.test.data.network

import com.google.gson.GsonBuilder
import com.wli.test.data.model.CommonModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class CustomApiCallback<T : CommonModel> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {

        @Suppress("UNCHECKED_CAST") val data =
            getResponse(response, CommonModel::class.java as Class<T>)
        val isError = isErrorInWebResponse(response.code())

        if (isError) {            // if error found display popup with message
            if (data?.message != null && data.message!!.isNotEmpty()) {
                handleResponseData(response.body())
            } else {
                showErrorMessage("Something went wrong. Please try again")
            }
        } else {
            handleResponseData(response.body())
        }
    }

    protected abstract fun handleResponseData(data: T?)

    protected abstract fun showErrorMessage(errormessage: String)

    /**
     * Handle Failure case
     */
    override fun onFailure(call: Call<T>, t: Throwable) {
        if (isKnownException(t)) {
            showErrorMessage("Due to network connection error we\\'re having trouble.")
        } else {
            showErrorMessage("Something went wrong. Please try again")
        }
    }

    //for showing known exception
    private fun isKnownException(t: Throwable): Boolean {
        return (t is ConnectException
                || t is UnknownHostException
                || t is SocketTimeoutException
                || t is IOException)
    }

    // check whether is there any error in web response
    private fun isErrorInWebResponse(statusCode: Int): Boolean {
        // Handler handler = new Handler();
        return when (statusCode) {
            200 -> false
            400 -> true
            401 -> true
            404 -> true
            500 -> true
            else -> true
        }
    }

    //Checking response code if not proper or null then showing this message
    private fun <T> getResponse(tResponse: Response<T>, tClass: Class<T>): T? {
        if (tResponse.code() in 200..299) {
            var t = tResponse.body()

            if (t == null) {
                t = GsonBuilder().create().fromJson(createErrorMsgJson(), tClass)
            }
            return t
        } else {
            val errorConverter =
                WebServiceClient.retrofitClient!!.responseBodyConverter<T>(tClass, arrayOfNulls(0))
            return try {
                errorConverter.convert(tResponse.errorBody()!!)
            } catch (e: IOException) {
                e.printStackTrace()
                GsonBuilder().create().fromJson(createErrorMsgJson(), tClass)
            }

        }
    }

    /**
     * Create custom error json
     */
    private fun createErrorMsgJson(): String {
        return "\n" +
                "{\n" +
                "  \"Status\": true,\n" +
                "  \"StatusCode\": 0,\n" +
                "  \"Message\": \"Due to network connection error we\\'re having trouble\",\n" +
                "  \"Result\": {\n" +
                "  \n" +
                "  }\n" +
                "}"
    }
}