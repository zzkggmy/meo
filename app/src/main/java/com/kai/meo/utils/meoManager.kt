package com.kai.meo.utils

fun setToken(token: String) {
    spSetString("token", token)
}

val getToken = getString("token","")

fun setThemeColor(color: String) {
    spSetString("color", color)
}

val getThemeColor = getString("color","#1E8AE8")