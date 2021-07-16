package com.example.top5movies.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.top5movies.R
import com.example.top5movies.data.model.MoviesMetadata
import com.example.top5movies.databinding.MovieItemBinding
import com.example.top5movies.utils.FLICKER_BASE_URL_PICTURE
import com.example.top5movies.utils.extensions.loadWithOptions

/*
* The class movieAdapter
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to show the list data in the movie fragment screen, it  has an inner class of movieViewHolder nad override methods of recyclerview. It has a public
* method of setData() to provide the data  to the adapter, and setOnItemClickListener() to handle the single item click event.
*/

class movieAdapter(private val movieList: ArrayList<MoviesMetadata>, private val context: Context) :  RecyclerView.Adapter<movieAdapter.movieViewHolder>(){
    inner class movieViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): movieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return movieViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: movieViewHolder, position: Int) {
       val item = movieList[position]

        holder.binding.apply {
            itemArticleTitle.text = item.title
           /* var desc=item.genres.get(0)
            for(i in 1 until item.genres.size){
                desc=desc+" , "+item.genres.get(i)
            }
          //  itemPostDescription.text = desc
            var cast=item.cast.get(0)
            for(i in 1 until item.genres.size){
                cast=cast+" , "+item.cast.get(i)
            }
          //  itemPostAuthor.text = cast*/
            //check if the media has thumbnail image and set the image, In-case image not available set the placeholder for the image,It's must to add else case otherwise
            // recyclerview add image on empty row by it's own.
            if (!item.media.isNullOrEmpty()){
                val imageUrl: String? = FLICKER_BASE_URL_PICTURE +item.media
                itemMovieImage.loadWithOptions(imageUrl, isTransformed = false)
            }else{
                itemMovieImage.loadWithOptions(context.resources.getDrawable(R.drawable.ic_launcher_background))
            }
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(item) }
        }
    }
    override fun getItemCount(): Int = movieList.size

    fun addData(movieList: List<MoviesMetadata>){
        this.movieList.clear()
        this.movieList.addAll(movieList)
    }


    private var onItemClickListener: ((MoviesMetadata) -> Unit)? = null
    fun setOnItemClickListener(listener: (MoviesMetadata) -> Unit) {
        onItemClickListener = listener
    }
}