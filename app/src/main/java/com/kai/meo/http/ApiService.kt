package com.kai.meo.http

import com.kai.meo.bean.*
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    //获取首页数据
    @GET("v1/vertical/vertical")
    fun getMain(): Deferred<RecommendBean>

    //获取分类
    @GET("v1/vertical/category")
    fun getCategories(): Deferred<CategoriesBean>

    //获取分类下图片
    @GET("v1/vertical/category/{id}/vertical?")
    fun getCategoryPic(@Path("id") id: String): Deferred<CategoryPicBean>

    //获取评论
    @GET("v2/vertical/vertical/{id}/comment")
    fun getComment(@Path("id") id: String): Deferred<CommentBean>

    //获取电脑壁纸分类
    @GET("v1/wallpaper/category")
    fun getComputerWallpaperCategory(): Deferred<ComputerWallpaperCategoryBean>

    //获取类别下电脑壁纸
    @GET("v1/wallpaper/category/{id}/wallpaper?")
    fun getComputerWallpaper(@Path("id") id: String): Deferred<ComputerWallpaperBean>

    //获取每日封面壁纸
    @GET("format=js&idx=0&n=1&mkt=zh-CN")
    fun getEverydayPic(): Deferred<EverydayPicBen>
}