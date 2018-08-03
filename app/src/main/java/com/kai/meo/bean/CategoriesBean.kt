package com.kai.meo.bean

data class CategoriesBean(var msg: String,
                          var res: Res,
                          var code: Int) {
    data class Res(var category: List<Category>) {
        data class Category(var count: Int,
                            var ename: String,
                            var rname: String,
                            var cover_temp: String,
                            var name: String,
                            var cover: String,
                            var rank: Int,
                            var sn: Int,
                            var icover: String,
                            var atime: Int,
                            var type: Int,
                            var id: String,
                            var picasso_cover: String,
                            var filter: List<Any>)
    }
}