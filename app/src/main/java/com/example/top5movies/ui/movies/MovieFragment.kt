package com.example.top5movies.ui.movie

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.example.top5movies.R
import com.example.top5movies.data.model.Movies
import com.example.top5movies.data.model.MoviesMetadata
import com.example.top5movies.databinding.FragmentMovieBinding
import com.example.top5movies.ui.base.BaseActivity
import com.example.top5movies.ui.base.BaseFragment
import com.example.top5movies.utils.BUNDLE_movie
import com.example.top5movies.utils.NO_INTERNET_CONNECTION
import com.example.top5movies.utils.SpacesItemDecorator
import com.example.top5movies.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


/*
* The class MovieFragment
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to show the movie data in the  fragment screen, this class is extended form generic base class BaseFragment and gets the data from the movieViewModel,
* populates the data in the screen on the basis of result form the api, In Success case shows the data, In loading case shows the shimmer loading, In error case show the
* error message with the option of retry button.
*/

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding>() {
    private lateinit var movieAdapter: movieAdapter
    private var movies: List<MoviesMetadata>? = null
    private var allMovies: List<MoviesMetadata>? = null
    private var responseRemaining = 0
    private var selectedYear = 0
    private val movieViewModel: MovieViewModel by activityViewModels()
    private val dataViewModel: MovieSharedViewModel by activityViewModels()
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMovieBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setup()
    }

    private fun setup() = with(binding) {
        visibleToolbar()
        initmovieRecyclerView()
        populateMovies()
        observeImages()
        movieItemOnClick()

    }

    private fun initmovieRecyclerView() = with(binding) {
        movieAdapter = movieAdapter(arrayListOf(), activity as BaseActivity).also {
            movieRv.adapter = it
            movieRv.addItemDecoration(SpacesItemDecorator(16))
        }
    }

    private fun populateMovies() {
        val jsonFileString = getJsonDataFromAsset(this.requireContext(), "movies.json")
        var data: Movies? = null
        val gson = Gson()
        data = gson.fromJson(jsonFileString, Movies::class.java)
        allMovies = data.MoviesMetadata!!
        setSpinner()

        //renderList(data.MoviesMetadata!!)
    }

    private fun callApis() {
        for (i in 0 until movies!!.size) {
            movieViewModel.fetchMovieImage(i, movies!!.get(i).title!!)
        }
        responseRemaining = movies!!.size

    }

    private fun setSpinner() {

        val years = ArrayList<String>()
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 0 until allMovies!!.size) {
            if (!years.contains(allMovies!!.get(i).year.toString())) {
                years.add(allMovies!!.get(i).year.toString())
            }
        }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(R.layout.spinner_list)
        binding.yearsSpinner.adapter = adapter
        val onYearSpinnerSelected: OnItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                try {
                    (parent.getChildAt(0) as TextView).setTextColor(Color.BLACK)
                    (parent.getChildAt(0) as TextView).textSize = 16f
                    var year = years.get(pos).toInt()
                    selectedYear = year
                    movies = allMovies!!.filter { it.year == year }
                    var prevdata = dataViewModel.getListData(year)
                    if (prevdata.isNullOrEmpty()) {
                        movies = movies!!.subList(0, 5)
                        callApis()
                    } else {
                        movies = prevdata
                        renderList()
                    }
                } catch (e: Exception) {
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.yearsSpinner.onItemSelectedListener = onYearSpinnerSelected
        binding.yearsSpinner.setSelection(0, true)
    }

    private fun observeImages() {

        movieViewModel.imageRead.observe(viewLifecycleOwner) { image ->
            try {
                when (image.status) {
                    Status.SUCCESS -> {
                        responseRemaining--
                        hideShimmerLoading()
                        hideErrorScreen()
                        if (image.data != null) {
                            if (image.data.stat.equals("ok", true)) {
                                if (movies != null) {
                                    var img = image.data.photos.photo[0]
                                    movies!![image.data.index!!].media =
                                        img.server + "/" + img.id + "_" + img.secret + ".jpg"
                                    //  renderList(movies!!)
                                }
                                if (responseRemaining <= 0) {
                                    dataViewModel.setListData(selectedYear, movies!!)
                                    renderList()
                                }

                            }
                        }
                    }
                    Status.LOADING -> {
                        showShimmerLoading()
                        hideErrorScreen()
                    }

                    Status.ERROR -> {
                        responseRemaining--
                        if (image.message?.equals(NO_INTERNET_CONNECTION)!!) {
                            showErrorScreen(NO_INTERNET_CONNECTION)
                        } else {
                            showErrorScreen(image.message, isConnectionError = false)
                        }
                        hideShimmerLoading()

                        if (responseRemaining <= 0) {
                            dataViewModel.setListData(selectedYear, movies!!)
                            renderList()
                        }

                    }
                }
            } catch (e: Exception) {
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        lifecycleScope.launch {
            val isChecked = movieViewModel.uiModeRead.uiMode.first()
            val item = menu.findItem(R.id.action_night_mode)
            item.isChecked = isChecked
            setUIMode(item, isChecked)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_night_mode -> {
                item.isChecked = !item.isChecked
                setUIMode(item, item.isChecked)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUIMode(item: MenuItem, isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            movieViewModel.updateUiModeState(true)
            item.setIcon(R.drawable.ic_night)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            movieViewModel.updateUiModeState(false)
            item.setIcon(R.drawable.ic_day)
        }
    }

    private fun renderList() {
        movieAdapter.addData(movies!!)
        binding.movieRv.animate().alpha(5f)
        movieAdapter.notifyDataSetChanged()
    }

    private fun movieItemOnClick() {
        // pass bundle onclick
        movieAdapter.setOnItemClickListener { movie ->
            val bundle = Bundle()
            bundle.putSerializable(BUNDLE_movie, movie)

            findNavController().navigate(
                R.id.action_movieFragment_to_movieDetailFragment,
                bundle
            )
        }
    }

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun visibleToolbar() {
        if ((activity as BaseActivity).binding.toolbar.visibility == View.GONE) (activity as BaseActivity).binding.toolbar.visibility =
            View.VISIBLE
    }

    private fun showShimmerLoading() {
        binding.shimmerViewContainer.startShimmer()
        binding.loadingView.visibility = View.VISIBLE
    }

    private fun hideShimmerLoading() {
        binding.shimmerViewContainer.stopShimmer()
        binding.loadingView.visibility = View.GONE
    }

    private fun showErrorScreen(errorMessage: String, isConnectionError: Boolean = true) {
        binding.errorLayout.root.visibility = View.VISIBLE
        binding.errorLayout.tvErrorMessage.text = errorMessage
        binding.errorLayout.btnRetry.setOnClickListener {
            callApis()
        }
        if (isConnectionError) binding.errorLayout.imageView.visibility = View.VISIBLE
        else binding.errorLayout.imageView.visibility = View.GONE
    }

    private fun hideErrorScreen() {
        if (binding.errorLayout.root.visibility == View.VISIBLE) binding.errorLayout.root.visibility =
            View.GONE
    }
}
