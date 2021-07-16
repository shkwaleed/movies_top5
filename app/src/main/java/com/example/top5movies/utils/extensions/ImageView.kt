package com.example.top5movies.utils.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
/*
* The file ImageView
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used ot create the extension for the image loading using the coil-image-loading library, It has two methods, one of them is used to load form the url,
* other method takes the drawable object in the parameter. Both of the extensions take isTransformed boolean object to check either provide the radius to the image or not
*
*/

fun ImageView.loadWithOptions(url: String?, isTransformed:Boolean = true){
    this.load(url) {
        crossfade(true)
        crossfade(200)
        if(isTransformed) {
            transformations(
                RoundedCornersTransformation(
                    12f,
                    12f,
                    12f,
                    12f
                )
            )
        }
    }
}
fun ImageView.loadWithOptions( drawable: Drawable, isTransformed:Boolean = true){
    this.load(drawable) {
        crossfade(true)
        crossfade(200)
        if(isTransformed) {
            transformations(
                RoundedCornersTransformation(
                    12f,
                    12f,
                    12f,
                    12f
                )
            )
        }
    }
}
