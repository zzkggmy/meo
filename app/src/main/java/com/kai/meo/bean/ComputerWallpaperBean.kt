package com.kai.meo.bean

data class ComputerWallpaperBean(var msg: String,
                                 var res: Res,
                                 var code: Int) {
    data class Res(var wallpaper: List<Wallpaper>) {
        data class Wallpaper(var views: Int,
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
                             var preview: String,
                             var store: String,
                             var tag: List<String>,
                             var cid: List<String>,
                             var url: List<Any>)
    }
}