package com.languages4u.auth


interface IAuth {
    fun loginClicked()
    fun signUpClicked()
    fun passwordForgottenClicked()
    fun signUp()
    fun login()
    fun loginAnonymously()
    fun loginGoogle()
    fun loginFacebook()
}