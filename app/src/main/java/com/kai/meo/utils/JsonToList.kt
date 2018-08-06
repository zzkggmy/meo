package com.kai.meo.utils

import com.google.gson.JsonObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


object JsonToList {

    fun getJson(): String {
        val stringBuilder = StringBuilder()
        //获得assets资源管理器
        val assetManager = Common.context.getAssets()
        //使用IO流读取json文件内容
        try {
            val bufferedReader = BufferedReader(InputStreamReader(
                    assetManager.open("theme.json"), "utf-8"))
            var line: String = bufferedReader.readLine()
            while (line.isNotEmpty()) stringBuilder.append(line)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }

//    fun transform() {
//        val jsonObject: JsonObject = JsonObject(getJson())
//        gson
//    }
}