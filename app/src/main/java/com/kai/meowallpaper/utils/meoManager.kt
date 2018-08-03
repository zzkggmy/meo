package com.kai.meowallpaper.utils

fun setToken(token: String) {
    spSetString("token", token)
}

val getToken = getString("token","")