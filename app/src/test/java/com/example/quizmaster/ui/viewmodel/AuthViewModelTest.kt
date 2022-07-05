package com.example.quizmaster.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class AuthViewModelTest {

    lateinit var vm: AuthViewModel
//    lateinit var signUpState: MutableLiveData<String>

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: AuthRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        vm = AuthViewModel(repo)


    }

//
//    @Test
//    fun getSignUpStateTest() {

//        signUpState = MutableLiveData<String>()
//        signUpState.value = "pass"
//        assertEquals(vm.signUpState.value, "pass")
//        Mockito.`when`(repo.signUpState).thenReturn(signUpState)
//        println("Hello1   "+signUpState.value)
//        var result = vm.signUpState
//        println("Hello2   "+result.value)
//        assertEquals(result.value,"pass")

//        result.observe(this){
//            assertEquals(it,"pass")
//        }



//    }

    @Test
    fun isEmailVerified() {

        Mockito.`when`(repo.isEmailVerified()).thenReturn(true)
        val result = vm.isEmailVerified()
        assertEquals(result, true)
    }


    fun handleResetPasswordTets(email: String) {
        repo.handleResetPassword(email)
    }




//    @Test
//    fun handleSignIn() {
//    }
//
//    @Test
//    fun isEmailVerified() {
//    }
//
//    @Test
//    fun handleResetPassword() {
//    }
//
//    @Test
//    fun getRepo() {
//    }
//
//    @Test
//    fun setRepo() {
//    }
}