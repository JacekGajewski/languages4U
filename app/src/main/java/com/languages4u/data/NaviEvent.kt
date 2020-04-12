package com.languages4u.data

enum class NaviEvent(val event: String) {
    Authorization("AUTHORIZATION"),
    LogIn("LOGIN"),
    SingUp("SINGUP"),
    GoogleSingIn("GOOGLESINGIN"),
    FacebookSingIn("FACEBOOKSINGIN"),
    ForgotPass("FORGOTPASS"),
    MenuPage("MENUPAGE"),
    ChooseQuizPage("CHOOSEQUIZ"),
    StartQuizPage("STARTQUIZ"),

    //    ERRORS
    ForgotPassError("FORGOT_PASS_ERROR"),
    EmptyInput("EMPTY_INPUT"),


}