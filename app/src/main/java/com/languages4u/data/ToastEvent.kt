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
    SignInFail("Sign in failed"),
    NoQuestionAdded("Add at least one question"),
    NoQuizName("Add quiz name"),
    NoQuestion("Add question"),
    NoAnswer("Add all 3 possible answers"),

//    Profile Setting
    AccountDeleted("Account was successfully deleted"),
    ErrorAccountDeleted("Error occurred while deleting an account"),
    SuccessfulUpdate("Account successfully updated"),
    NicknameEmpty("Please enter a nickname"),
    ErrorNicknameUpdate("Error occurred while updating a nickname")

}