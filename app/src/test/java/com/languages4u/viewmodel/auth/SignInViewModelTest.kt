package com.languages4u.viewmodel.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.languages4u.data.DataOperations
import com.languages4u.data.NaviEvent
import com.languages4u.data.ToastEvent
import com.languages4u.viewmodel.auth.SignUpViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import kotlin.Exception


class SignInViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var signInViewModel : SignInViewModel

    @Mock
    lateinit var toastMsgObserver : Observer<String>

    @Mock
    lateinit var naviPageObserver : Observer<String>

    @Mock
    lateinit var firebase : DataOperations


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.signInViewModel = SignInViewModel()
    }

    @Test
    fun test_onLoginClick() {

        signInViewModel.firebase = firebase

        // Email Null or blank
        signInViewModel.email.value = ""
        signInViewModel.onLoginClick()
        //verify(toastMsgObsever).onChanged(ToastEvent.EmptyEmail.event)
        assert(signInViewModel.toastMsg.value == ToastEvent.EmptyEmail.event)

        // Email doesn't match pattern
        signInViewModel.email.value = "email.gmail.com@"
        signInViewModel.onLoginClick()
        //verify(toastMsgObserver).onChanged(ToastEvent.EmailInvalid.event)
        assert(signInViewModel.toastMsg.value == ToastEvent.EmailInvalid.event)

        // Password Null or blank
        signInViewModel.email.value = "email@gmail.com"
        signInViewModel.password.value = ""
        signInViewModel.onLoginClick()
        //verify(toastMsgObserver).onChanged(ToastEvent.EmptyPassword.event)
        assert(signInViewModel.toastMsg.value == ToastEvent.EmptyPassword.event)

        // Email and password ok
        signInViewModel.email.value = "email@gmail.com"
        signInViewModel.password.value = "password"
        signInViewModel.onLoginClick()
        verify(firebase).login("email@gmail.com", "password", signInViewModel)
    }

    @Test
    fun test_onLoginAnonymClick() {
        signInViewModel.firebase = firebase
        signInViewModel.onLoginAnonymClick()
        verify(firebase).loginAnonymously(signInViewModel)
    }

    @Test
    fun test_onForgotPassword() {
        signInViewModel.onForgotPasswordClick()
        assert(signInViewModel.navigatePage.value == NaviEvent.ForgotPass.event)
    }

    @Test
    fun test_onSuccess() {
        signInViewModel.firebase = firebase
        signInViewModel.onSuccess()
        assert(signInViewModel.navigatePage.value == NaviEvent.MenuPage.event)
    }

    @Test
    fun test_onFailure() {
        val msg = "exceptionmsg"
        val exception = Exception(msg)
        signInViewModel.onFailure(exception)
        assert(signInViewModel.toastMsg.value == msg)
    }
}