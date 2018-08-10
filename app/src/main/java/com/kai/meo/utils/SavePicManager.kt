package com.kai.meo.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import java.io.*

object SavePicManager {
    fun savePhoto(imgUrl: String) {
        Thread(Runnable {
            val bitmap: Bitmap = getPic(imgUrl)!!
            saveBitmap(bitmap)
        }).start()

    }
}

/*
* 通过图片url拿到图片信息
* */
private fun getPic(imgUrl: String): Bitmap? {

    try {
        val client = OkHttpClient()
        val request: Request = Request.Builder().url(imgUrl).build()
        val responseBody: ResponseBody = client.newCall(request).execute().body()!!
        val input: InputStream = responseBody.byteStream()
        return BitmapFactory.decodeStream(input)
    }catch (e: IOException){}

    return null
}

private fun saveBitmap(bitmap: Bitmap) {
    /*自定义文件夹存放图片*/
    val fileDir = File(Environment.getExternalStorageDirectory(),"meo")

    if (!fileDir.exists()){
        fileDir.mkdir()
    }
    /*根据系统时间创建每个图片的文件名*/
    val fileName: String = "" + System.currentTimeMillis() + ".jpg"
    val file = File(fileDir,fileName)

    try {
        /*写入图片到本地存储*/
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
        fos.flush()
        fos.close()
        val uri: Uri = Uri.fromFile(file)
        Common.context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
    }catch (e: FileNotFoundException){}

    try {
//                MediaStore.Images.Media.insertImage(activity.contentResolver,file.absolutePath,fileName,null)
    }catch (e: FileNotFoundException){}
}