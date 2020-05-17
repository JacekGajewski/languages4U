package com.languages4u.viewmodel

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


class SignUpViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var signUpViewModel : SignUpViewModel

    @Mock
    lateinit var toastMsgObserver : Observer<String>

    @Mock
    lateinit var naviPageObserver : Observer<String>

    @Mock
    lateinit var firebase : DataOperations


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.signUpViewModel = SignUpViewModel()
    }

    @Test
    fun test_onSignUpClick() {

        // Zakomentowałem część kodu, uważam, że powinno być to testowane przez
        // sprawdzanie czy po zmianie wartości obiektu LiveData na VAL zotała wywołana metoda onChange(VAL)
        // problem jest taki, że jeśli kiedyś w przeszłości na obserwatorze została już wywołana
        // metoda onChange(VAL) to zawsze pózniej verify(observer).onChanged(VAL) zwróci true
        // Sprawdzanie czy zwyczajnie zmieniła się wartość LiveData realizuje ten sam scenariusz
        // ale mniej profesjonalnie
        //signUpViewModel.toastMsg.observeForever(toastMsgObserver)
        //signUpViewModel.navigatePage.observeForever(naviPageObserver)
        signUpViewModel.firebase = firebase

        // Email Null or blank
        signUpViewModel.email.value = ""
        signUpViewModel.onSignUpClick()
        //verify(toastMsgObsever).onChanged(ToastEvent.EmptyEmail.event)
        assert(signUpViewModel.toastMsg.value == ToastEvent.EmptyEmail.event)

        // Email doesn't match pattern
        signUpViewModel.email.value = "email.gmail.com@"
        signUpViewModel.onSignUpClick()
        //verify(toastMsgObserver).onChanged(ToastEvent.EmailInvalid.event)
        assert(signUpViewModel.toastMsg.value == ToastEvent.EmailInvalid.event)

        // Password Null or blank
        signUpViewModel.email.value = "email@gmail.com"
        signUpViewModel.password.value = ""
        signUpViewModel.onSignUpClick()
        //verify(toastMsgObserver).onChanged(ToastEvent.EmptyPassword.event)
        assert(signUpViewModel.toastMsg.value == ToastEvent.EmptyPassword.event)

        // Passwords do not match
        signUpViewModel.email.value = "email@gmail.com"
        signUpViewModel.password.value = "password"
        signUpViewModel.repeatPassword.value = "password1"
        signUpViewModel.onSignUpClick()
        //verify(toastMsgObserver).onChanged(ToastEvent.PassNotMatch.event)
        assert(signUpViewModel.toastMsg.value == ToastEvent.PassNotMatch.event)

        // Email nad password ok
        signUpViewModel.email.value = "email@gmail.com"
        signUpViewModel.password.value = "password"
        signUpViewModel.repeatPassword.value = "password"
        signUpViewModel.onSignUpClick()
        verify(firebase).register("email@gmail.com", "password", signUpViewModel)
    }

    @Test
    fun test_onLoginAnnonymClick() {
        signUpViewModel.firebase = firebase
        signUpViewModel.onLoginAnnonymClick()
        verify(firebase).loginAnonymously(signUpViewModel)
    }

    @Test
    fun test_onSuccess() {
        signUpViewModel.firebase = firebase
        signUpViewModel.onSuccess()
        assert(signUpViewModel.navigatePage.value == NaviEvent.MenuPage.event)
    }

    @Test
    fun test_onFailure() {
        val msg = "exceptionmsg"
        val exception = Exception(msg)
        signUpViewModel.onFailure(exception)
        assert(signUpViewModel.toastMsg.value == msg)
    }
}