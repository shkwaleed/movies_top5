package com.example.top5movies.utils

import com.example.top5movies.data.model.ImageResponse

fun mockImageResource() = ImageResponse(mockPhotos(), "2" ,23)

fun mockPhotos() = ImageResponse.Photos(
    1,
    12,
    2,
    listOf(
        (ImageResponse.Photos.Photo(1, "21", 1, 1,1,"waleed","yes","dummy","dummy title")),
        (ImageResponse.Photos.Photo(1, "21", 1, 1,1,"waleed","yes","dummy","dummy title")),
        (ImageResponse.Photos.Photo(1, "21", 1, 1,1,"waleed","yes","dummy","dummy title")),
        (ImageResponse.Photos.Photo(1, "21", 1, 1,1,"waleed","yes","dummy","dummy title")),
        (ImageResponse.Photos.Photo(1, "21", 1, 1,1,"waleed","yes","dummy","dummy title")
    )
    ),
    34
)