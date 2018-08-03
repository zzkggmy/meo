package com.kai.meowallpaper.http

import com.kai.meowallpaper.bean.CategoriesBean
import com.kai.meowallpaper.bean.CategoryPicBean
import com.kai.meowallpaper.bean.CommentBean
import com.kai.meowallpaper.bean.RecommendBean
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


}