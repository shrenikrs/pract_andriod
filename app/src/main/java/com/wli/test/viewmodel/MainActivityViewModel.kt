package com.wli.test.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wli.test.data.model.User
import com.wli.test.data.model.UserData
import com.wli.test.data.repository.UserListRepository
import com.wli.test.utils.CommonApiState
import com.wli.test.utils.Resource
import com.wli.test.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: UserListRepository) :
    ViewModel() {

    val _userState = MutableStateFlow(
        CommonApiState(
            Status.LOADING,
            UserData(), ""
        )
    )

    init {
        getUser()
    }

    fun getUser() {
        _userState.value = CommonApiState.loading()

        viewModelScope.launch {
            repository.getRandomUser().collect{
                _userState.value = CommonApiState.success(it.data)
            }

        }
    }
}

