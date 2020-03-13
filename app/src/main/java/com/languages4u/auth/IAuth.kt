package com.languages4u.auth


interface IAuth {
    fun loginClicked()
    fun signUpClicked()
    fun passwordForgottenClicked()
    fun signUp(email: String, password: String)
    fun login(email: String, password: String)
    fun loginAnonymously()
    fun loginGoogle()
    fun loginFacebook()
}