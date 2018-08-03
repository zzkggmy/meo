package com.kai.meowallpaper.bean

data class RecommendBean(var msg: String,
                         var res: Res,
                         var code: Int) {
    data class Res(var vertical: List<Vertical>) {
        data class Vertical(var views: Int,
                            var ncos: Int,
                            var rank: Int,
                            var wp: String,
                            var xr: Boolean,
                            var cr: Boolean,
                            var favs: Int,
                            var atime: Int,
                            var id: String,
                            var desc: String,
                            var thumb: String,
                            var img: String,
                            var rule: String,
                            var preview: String,
                            var store: String,
                            var tag: List<Any>,
                            var cid: List<String>,
                            var url: List<Any>)
    }
}