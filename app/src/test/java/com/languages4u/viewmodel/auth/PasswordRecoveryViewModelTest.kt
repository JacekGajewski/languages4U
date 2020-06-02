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


class PasswordRecoveryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var passRecViewModel: PasswordRecoveryViewModel

    @Mock
    lateinit var toastMsgObserver : Observer<String>

    @Mock
    lateinit var naviPageObserver : Observer<String>

    @Mock
    lateinit var firebase : DataOperations


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.passRecViewModel = PasswordRecoveryViewModel()
    }

    @Test
    fun test_onResetClicked() {

        passRecViewModel.firebase = firebase

        // Email Null or blank
        passRecViewModel.email.value = ""
        passRecViewModel.onResetClicked()
        //verify(toastMsgObsever).onChanged(ToastEvent.EmptyEmail.event)
        assert(passRecViewModel.toastMsg.value == ToastEvent.EmptyEmail.event)

        // Email doesn't match pattern
        passRecViewModel.email.value = "email.gmail.com@"
        passRecViewModel.onResetClicked()
        //verify(toastMsgObserver).onChanged(ToastEvent.EmailInvalid.event)
        assert(passRecViewModel.toastMsg.value == ToastEvent.EmailInvalid.event)

        // Email and password ok
        passRecViewModel.email.value = "email@gmail.com"
        passRecViewModel.onResetClicked()
        verify(firebase).recoverPassword("email@gmail.com", passRecViewModel)
    }

    @Test
    fun test_onSuccess() {
        passRecViewModel.firebase = firebase
        passRecViewModel.onSuccess()
        assert(passRecViewModel.navigatePage.value == NaviEvent.Authorization.event)
    }

    @Test
    fun test_onFailure() {
        val msg = "exceptionmsg"
        val exception = Exception(msg)
        passRecViewModel.onFailure(exception)
        assert(passRecViewModel.toastMsg.value == msg)
    }
}