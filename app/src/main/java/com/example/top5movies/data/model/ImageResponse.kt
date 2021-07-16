package com.example.top5movies.data.model


import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("photos")
    val photos: Photos,
    @SerializedName("stat")
    val stat: String, // ok
    var index:Int?=null
) {
    data class Photos(
        @SerializedName("page")
        val page: Int, // 1
        @SerializedName("pages")
        val pages: Int, // 25681
        @SerializedName("perpage")
        val perpage: Int, // 1
        @SerializedName("photo")
        val photo: List<Photo>,
        @SerializedName("total")
        val total: Int // 25681
    ) {
        data class Photo(
            @SerializedName("farm")
            val farm: Int, // 66
            @SerializedName("id")
            val id: String, // 51305316774
            @SerializedName("isfamily")
            val isfamily: Int, // 0
            @SerializedName("isfriend")
            val isfriend: Int, // 0
            @SerializedName("ispublic")
            val ispublic: Int, // 1
            @SerializedName("owner")
            val owner: String, // 188684121@N05
            @SerializedName("secret")
            val secret: String, // 00a0a46e46
            @SerializedName("server")
            val server: String, // 65535
            @SerializedName("title")
            val title: String // Adventures In Second Life -
        )
    }
}