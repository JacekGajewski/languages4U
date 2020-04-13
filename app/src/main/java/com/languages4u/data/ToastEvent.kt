package com.languages4u.data

enum class ToastEvent(val event : String) {
    WrongPassword("Wrong password"),
    WrongEmail("There is no account assigned to this email address"),
    WrongCredentials("Incorrect email address or password"),
    PassNotMatch("Passwords don't match"),
    EmptyEmail("Please enter an email address"),
    EmptyPassword("Please enter a password"),
    EmailInvalid("Please enter a valid email address"),
    ResetPasswordSuccess("An email with instructions was send to your address"),
    FacebookSignInError("Sign in with credentials failed"),
    SignInCancelled("Sign in cancelled"),
    SignInFail("Sign in failed")
}