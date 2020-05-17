package com.languages4u.data

enum class NaviEvent(val event: String) {
    Authorization("AUTHORIZATION"),
    SignIn("SIGN_IN"),
    SingUp("SING_UP"),
    GoogleSingIn("GOOGLE_SING_IN"),
    FacebookSingIn("FACEBOOK_SIGN_IN"),
    ForgotPass("FORGOT_PASSWORD"),
    MenuPage("MENU_PAGE"),
    ChooseQuizPage("CHOOSE_QUIZ"),
    StartQuizPage("START_QUIZ"),
    HomePage("HOME_PAGE"),
    AddQuiz("ADD_QUIZ"),
    AddQuestion("ADD_QUESTION")
    }