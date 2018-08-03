package com.kai.meo.bean

data class CommentBean(var msg: String,
                       var res: Res,
                       var code: Int) {
    data class Res(var meta: Meta,
                   var vertical: Vertical,
                   var comment: List<Comment>,
                   var hot: List<Hot>) {
        data class Meta(var more: Boolean)
        data class Vertical(var isfavor: Boolean)
        data class Comment(var reply_user: ReplyUser,
                           var reply_meta: ReplyMeta,
                           var content: String,
                           var isup: Boolean,
                           var user: User,
                           var atime: Int,
                           var id: String,
                           var size: Int) {
            data class ReplyUser(var a: String)
            data class ReplyMeta(var a: String)
            data class User(var gcid: String,
                            var name: String,
                            var gender: Int,
                            var follower: Int,
                            var avatar: String,
                            var viptime: Int,
                            var following: Int,
                            var isvip: Boolean,
                            var id: String,
                            var title: List<Any>)
        }

        data class Hot(var reply_user: ReplyUser,
                       var reply_meta: ReplyMeta,
                       var content: String,
                       var isup: Boolean,
                       var user: User,
                       var atime: Int,
                       var id: String,
                       var size: Int) {
            data class ReplyUser(var a: String)
            data class ReplyMeta(var a: String)
            data class User(var gcid: String,
                            var name: String,
                            var gender: Int,
                            var follower: Int,
                            var avatar: String,
                            var viptime: Int,
                            var following: Int,
                            var isvip: Boolean,
                            var id: String,
                            var title: List<Any>)
        }
    }
}