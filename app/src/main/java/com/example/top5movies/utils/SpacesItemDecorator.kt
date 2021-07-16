package com.example.top5movies.utils

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
/*
* The class SpacesItemDecorator
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class used to set the padding between the views of the recyclerview single item
*/
class SpacesItemDecorator(space: Int) : RecyclerView.ItemDecoration() {

    private val space: Int = space.dp

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val orientation = (parent.layoutManager as? LinearLayoutManager)?.orientation
            ?: LinearLayoutManager.VERTICAL
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            when (parent.getChildLayoutPosition(view)) {
                0 -> {
                    outRect.left = space
                    outRect.right = space
                }
                else -> outRect.right = space
            }
        } else {
            when (parent.getChildLayoutPosition(view)) {
                0 -> {
                    outRect.top = space
                    outRect.left = space
                    outRect.right = space
                    outRect.bottom = space
                }
                else -> {
                    outRect.left = space
                    outRect.right = space
                    outRect.bottom = space
                }
            }
        }
    }
}

val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()
