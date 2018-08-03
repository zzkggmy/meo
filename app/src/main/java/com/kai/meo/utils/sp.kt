package com.kai.meo.utils

import android.content.Context
import android.content.SharedPreferences

val sp: SharedPreferences by lazy { com.kai.meo.App.instance.applicationContext.getSharedPreferences("meo", Context.MODE_PRIVATE) }

fun spSetString(key: kotlin.String,value: kotlin.String) = sp.edit().putString(key, value).apply()

fun getString(key: kotlin.String,defaultValue: kotlin.String) : String{
    return sp.getString(key,defaultValue)
}

fun spSetInt(key: kotlin.String,value: kotlin.Int) = sp.edit().putInt(key,value).apply()

fun getInt(key: kotlin.String,defaultValue: kotlin.Int): kotlin.Int {
    return sp.getInt(key,defaultValue)
}

fun spSetBoolean(key: kotlin.String,value: kotlin.Boolean) = sp.edit().putBoolean(key,value).apply()

fun getBoolean(key: kotlin.String,defaultValue: kotlin.Boolean) = sp.getBoolean(key,defaultValue)