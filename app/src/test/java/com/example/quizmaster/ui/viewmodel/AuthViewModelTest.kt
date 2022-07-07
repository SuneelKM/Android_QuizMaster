package com.example.quizmaster.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.quizmaster.data.repo.AuthRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
//import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


@RunWith(JUnit4::class)
class AuthViewModelTest {

    lateinit var vm: AuthViewModel
//    lateinit var signUpState: MutableLiveData<String>

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: AuthRepository

    @Mock
    lateinit var signUpStateObserver: Observer<String>

    @Mock
    lateinit var signUpState:MutableLiveData<String>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        vm = AuthViewModel(repo)
//        vm.signUpState.observeForever(signUpStateObserver)

    }

    @Test
    fun handleSignUpTest(){
        vm.handleSignUp("sk","sk@gmail.com","123456")
        verify(repo, times(1)).handleSignUp("sk","sk@gmail.com","123456")
//        verify(vm.signUpState, equals("success") )
        assertEquals(vm.signUpState, "success")
    }

//    @Test
//    fun getSignUpStateTest(){
//
//    }

//
    @Test
    fun getSignUpStateTest() {

//    var signUpState = MutableLiveData<String>()
    signUpState.postValue("pass")

    Mockito.`when`(vm.signUpState.value).thenReturn("pass")
    assertEquals(vm.signUpState.value, "pass")
    Mockito.`when`(repo.signUpState).thenReturn(signUpState)
    vm.handleSignUp("sk", "skk@gmail.com", "123456")
    var result = vm.signUpState
    assertEquals(result.value, "pass")
}


//    }


//    @Test
//    fun isEmailVerified() {
//
//        Mockito.`when`(repo.isEmailVerified()).thenReturn(true)
//        val result = vm.isEmailVerified()
//        assertEquals(result, true)
//    }
//
//
//    fun handleResetPasswordTets(email: String) {
//        repo.handleResetPassword(email)
//    }
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
