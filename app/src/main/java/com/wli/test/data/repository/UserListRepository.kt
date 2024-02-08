package com.wli.test.data.repository

import com.wli.test.data.model.UserData
import com.wli.test.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface UserListRepository {

    suspend fun getRandomUser(): Flow<Resource<UserData>>
}