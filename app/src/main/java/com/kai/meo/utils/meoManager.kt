package com.kai.meo.utils

fun setToken(token: String) {
    spSetString("token", token)
}

val getToken = getString("token","")