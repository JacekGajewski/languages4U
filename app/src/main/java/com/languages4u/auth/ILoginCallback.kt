package com.languages4u.auth

import java.lang.Exception

interface ILoginCallback {
    fun onSuccess()
    fun onFailure(exception: Exception?)
}