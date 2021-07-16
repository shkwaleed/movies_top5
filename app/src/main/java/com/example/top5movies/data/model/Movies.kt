package com.example.top5movies.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movies(
    @SerializedName("MoviesMetadata")
    val MoviesMetadata: List<MoviesMetadata>? = null
) : Serializable