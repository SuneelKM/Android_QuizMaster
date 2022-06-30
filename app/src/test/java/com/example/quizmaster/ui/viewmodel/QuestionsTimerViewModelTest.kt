package com.example.quizmaster.ui.viewmodel


import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class QuestionsTimerViewModelTest {


    lateinit var vm: QuestionsTimerViewModel


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        vm = QuestionsTimerViewModel()

    }


    @Test
    fun getLiveTimerTest() {
        val result = vm.getLiveTimer().value
        assertEquals(result, null)
    }


    @Test
    fun getFinishTest() {
        val result = vm.getFinish().value
        assertEquals(result, null)
    }



}


