package com.example.top5movies.ui.moviedetail


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.top5movies.R

import com.example.top5movies.databinding.FragmentMovieDetailBinding
import com.example.top5movies.ui.base.BaseActivity
import com.example.top5movies.ui.base.BaseFragment
import com.example.top5movies.ui.movie.MovieSharedViewModel
import com.example.top5movies.utils.FLICKER_BASE_URL_PICTURE
import com.example.top5movies.utils.extensions.loadWithOptions

/*
* The class movieDetailFragment
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to show the movie data of single movie selected from the  movie fragment screen, this class is extended form generic base class BaseFragment and gets the data from the MovieDetailFragmentArgs,
* populates the data in the screen on the basis of result form the bundle data received.
*/
@Suppress("DEPRECATION")
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    private val args: MovieDetailFragmentArgs by navArgs()
    private var isImageUrlAvailable: Boolean = false
    private val dataViewModel: MovieSharedViewModel by activityViewModels()
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMovieDetailBinding.inflate(inflater, container, false)

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie
        hideToolbar()
        binding.apply {
            tvTitle.text = movie.title
             var desc= movie.genres[0]
            for(i in 1 until movie.genres.size){
                desc=desc+" , "+ movie.genres[i]
            }
            tvDescription.text = desc
            var cast= movie.cast[0]
            for(i in 1 until movie.genres.size){
                cast=cast+" , "+ movie.cast[i]
            }
            tvAuthor.text = cast
           /* tvDescription.text = movie.description
            tvAuthor.text = movie.byline*/
                if (movie.media!=null) {

                        val imageUrl: String? = FLICKER_BASE_URL_PICTURE+movie.media
                        if (!movie.media.isNullOrEmpty()) {
                            isImageUrlAvailable = true
                            ivPhoto.loadWithOptions(imageUrl, isTransformed = false)
                        }
                }

            if (!isImageUrlAvailable) ivPhoto.loadWithOptions((activity as BaseActivity).resources?.getDrawable(R.drawable.ic_launcher_background)!!,isTransformed = false)
            ivClose.setOnClickListener {
                (activity as BaseActivity).onBackPressed()
            }
        }

    }
private fun hideToolbar() {
    if((activity as BaseActivity).binding.toolbar.visibility == View.VISIBLE)  (activity as BaseActivity).binding.toolbar.visibility = View.GONE
}

}