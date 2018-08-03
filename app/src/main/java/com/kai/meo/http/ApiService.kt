package com.kai.meo.http

import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    //获取首页数据
    @GET("v1/vertical/vertical")
    fun getMain(): Deferred<com.kai.meo.bean.RecommendBean>


    //获取分类
    @GET("v1/vertical/category")
    fun getCategories(): Deferred<com.kai.meo.bean.CategoriesBean>

    //获取分类下图片
    @GET("v1/vertical/category/{id}/vertical?")
    fun getCategoryPic(@Path("id") id: String): Deferred<com.kai.meo.bean.CategoryPicBean>

    //获取评论
    @GET("v2/vertical/vertical/{id}/comment")
    fun getComment(@Path("id") id: String): Deferred<com.kai.meo.bean.CommentBean>


}