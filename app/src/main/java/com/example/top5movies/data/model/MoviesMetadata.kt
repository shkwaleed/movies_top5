package com.example.top5movies.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable
/*
* The class MediaMetadata
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is data class which holds the data fetching from movie- api, specifically holding the media-detail i.e image-url, format,width height.
* Note: Only the fields which are relevant according to the project scope is added, rest of all is ignored.
*/
data class MoviesMetadata(
    @SerializedName("cast")
    val cast: List<String>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("rating")
    val rating: Int, // 1
    @SerializedName("title")
    val title: String, // (500) Days of Summer
    @SerializedName("year")
    val year: Int, // 2009
    @SerializedName("media")
    var media:String=""
):Serializable