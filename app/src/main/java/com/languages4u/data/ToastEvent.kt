package com.languages4u.data

enum class ToastEvent(val event : String) {
    WrongPassword("Wrong password"),
    PassNotMatch("Passwords don't match"),
    ForgotPassError("FORGOT_PASS_ERROR"),
    EmptyEmail("Please enter an email address"),
    EmailInvalid("Please enter a valid email address"),
    ResetPasswordSuccess("An email with instructions was send to your address")
}