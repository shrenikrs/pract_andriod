package com.wli.test.data.repository

import com.wli.test.data.database.SampleDatabase
import com.wli.test.data.model.UserData
import com.wli.test.data.network.WebServiceAPI
import com.wli.test.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserListRepositoryImpl @Inject constructor(webServiceAPI: WebServiceAPI): UserListRepository {
    private val webServiceApi = webServiceAPI

    override suspend fun getRandomUser(): Flow<Resource<UserData>> = flow {
        // here logic for getting data from api and storing in db
        // then show updated data fetched from db following single source of truth principle
        emit(Resource.Loading())
        val data = webServiceApi.getUserList(10, "gender, name, picture, login, email, phone")
        if (!data.result.isNullOrEmpty()) {
            emit(Resource.Success(data = UserData(result = data.result)))
        }
    }.catch { throwable ->
        when (throwable) {
            is IOException -> emit(
                Resource.Error(
                    message = throwable.localizedMessage ?: "Check Connectivity"
                )
            )
            is HttpException -> emit(
                Resource.Error(
                    message = throwable.localizedMessage ?: "An Unknown error occurred"
                )
            )
            is Exception -> emit(
                Resource.Error(
                    message = throwable.localizedMessage ?: "Something went wrong!"
                )
            )
        }
    }


}