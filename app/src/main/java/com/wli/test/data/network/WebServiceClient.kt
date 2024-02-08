package com.wli.test.data.network

import android.annotation.SuppressLint
import android.content.Context
import com.wli.test.BuildConfig
import com.wli.test.ProjectName
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

//https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php
//http://velmm.com/apis/volley_array.json

class WebServiceClient
/**
 * Private constructor for singleton purpose
 */
private constructor() {
    private var retrofit: Retrofit? = null

    /**
     * The API reference
     */
    private var service: WebServiceAPI? = null

    /**
     * @return OkHttpClient with log and custom header interceptors
     */
    private val okHttpClient: OkHttpClient
        get() {
            val httpClient = OkHttpClient.Builder()
            //httpClient.cache(Cache(File(ProjectName.instance.cacheDir, "http-cache"), 100L * 1024L * 1024L)) // 100 MiB
            httpClient.addInterceptor(customHeaderInterceptor())
            httpClient.addInterceptor(loggingInterceptor())
            //httpClient.addNetworkInterceptor(CacheInterceptor())
            httpClient.readTimeout(READ_TIME_OUT_SEC.toLong(), TimeUnit.SECONDS)
            httpClient.connectTimeout(CONNECT_TIME_OUT_SEC.toLong(), TimeUnit.SECONDS)
            return httpClient.build()
        }

    /**
     *
     * Internal helper and initializer
     *
     */
    private fun initRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        service = retrofit!!.create(WebServiceAPI::class.java)
    }

    /**
     * @return Interceptor with custom header
     */
    private fun customHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            /**
             * add headers here
             */
            val request = original.newBuilder()
                .header(HEADER_TOKEN, "jfksldjfsalkslkdsa;ls1212121")
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }
    }

    /**
     * @return Interceptor that provides logging
     */
    private fun loggingInterceptor(): Interceptor {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    companion object {

        private val HEADER_TOKEN = "token"
        private val CONNECT_TIME_OUT_SEC = 60
        private val READ_TIME_OUT_SEC = 60

        /**
         * Static Object reference
         */
        @SuppressLint("StaticFieldLeak")
        private var webServiceClient: WebServiceClient? = null
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null

        /**
         * java.lang.String * will init retrofit. needs to be called before using API. preferably from Application class
         *
         * @param context
         */
        fun init(context: Context) {
            if (webServiceClient == null) {
                webServiceClient = WebServiceClient()
                webServiceClient!!.initRetrofit()
                mContext = context
            }
        }

        val retrofitClient: Retrofit?
            get() = webServiceClient!!.retrofit

        /**
         * @return Web API
         */
        fun getService(): WebServiceAPI? {
            return webServiceClient!!.service
        }


        val client: Retrofit?
            get() = webServiceClient!!.retrofit
    }
}