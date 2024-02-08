package com.wli.test

import com.wli.test.data.repository.TennisPlayerRepository
import com.wli.test.viewmodel.MainActivityViewModel
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThat
import org.junit.Rule
import org.junit.Test
import java.util.Collections

class MainActivityViewModelTest {

    private lateinit var repository: TennisPlayerRepository

    @Rule
    val main = MainActivityViewModel(repository)

    @Test
    fun `Should not have empty list`() {
        assertNotEquals(Collections.EMPTY_LIST, main.tennisPlayersState.value?.data)
    }
}