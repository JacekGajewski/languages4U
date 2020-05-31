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


class AuthViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var authViewModel: AuthViewModel

    @Mock
    lateinit var toastMsgObserver : Observer<String>

    @Mock
    lateinit var naviPageObserver : Observer<String>

    @Mock
    lateinit var firebase : DataOperations


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.authViewModel = AuthViewModel()
    }

    @Test
    fun test_onLoginClick() {
        authViewModel.onLoginClick()
        assert(authViewModel.navigatePage.value == NaviEvent.SignIn.event)
    }

    @Test
    fun test_onSignUp() {
        authViewModel.onSingUpClick()
        assert(authViewModel.navigatePage.value == NaviEvent.SingUp.event)
    }

    @Test
    fun test_onLoginAnonymClick() {
        authViewModel.firebase = firebase
        authViewModel.onLoginAnonymClick()
        verify(firebase).loginAnonymously(authViewModel)
    }

    @Test
    fun test_onPassRestoreClick() {
        authViewModel.onPassRestoreClick()
        assert(authViewModel.navigatePage.value == NaviEvent.ForgotPass.event)
    }

    @Test
    fun test_onLoginFacebookClick() {
        authViewModel.onLoginFacebookClick()
        assert(authViewModel.navigatePage.value == NaviEvent.FacebookSingIn.event)
    }

    @Test
    fun test_onLoginGoogleClick() {
        authViewModel.onLoginGoogleClick()
        assert(authViewModel.navigatePage.value == NaviEvent.GoogleSingIn.event)
    }

    @Test
    fun test_onSuccess() {
        authViewModel.firebase = firebase
        authViewModel.onSuccess()
        assert(authViewModel.navigatePage.value == NaviEvent.MenuPage.event)
    }

    @Test
    fun test_onFailure() {
        val msg = "exceptionmsg"
        val exception = Exception(msg)
        authViewModel.onFailure(exception)
        assert(authViewModel.toastMsg.value == msg)
    }
}