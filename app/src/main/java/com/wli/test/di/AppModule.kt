package com.wli.test.di

import android.content.Context
import com.wli.test.BuildConfig
import com.wli.test.data.database.SampleDatabase
import com.wli.test.data.network.WebServiceAPI
import com.wli.test.data.repository.UserListRepository
import com.wli.test.data.repository.UserListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private const val CONNECT_TIME_OUT_SEC = 60
    private const val READ_TIME_OUT_SEC = 60

    private val okHttpClient: OkHttpClient
        get() {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(customHeaderInterceptor())
            httpClient.addInterceptor(loggingInterceptor())
            httpClient.readTimeout(READ_TIME_OUT_SEC.toLong(), TimeUnit.SECONDS)
            httpClient.connectTimeout(CONNECT_TIME_OUT_SEC.toLong(), TimeUnit.SECONDS)
            return httpClient.build()
        }

    private fun customHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()

            /**
             * add headers here
             */
            val request = original.newBuilder()
                .header("token", "jfksldjfsalkslkdsa;ls1212121")
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

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideWebServiceApi(retrofit: Retrofit): WebServiceAPI {
        return retrofit.create(WebServiceAPI::class.java)
    }

    /*@Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): SampleDatabase {
        return SampleDatabase.getInstance(context)!!
    }*/

    @Singleton
    @Provides
    fun provideUserListRepository(webServiceAPI: WebServiceAPI
    ): UserListRepository {
        return UserListRepositoryImpl(webServiceAPI)
    }


}