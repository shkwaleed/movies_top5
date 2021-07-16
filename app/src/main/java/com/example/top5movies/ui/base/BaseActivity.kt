package com.example.top5movies.ui.base

import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.top5movies.R
import com.example.top5movies.databinding.ActivityBaseBinding
import com.example.top5movies.ui.movie.MovieSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
* The class BaseActivity
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
* This class is used to handle all the generic operations related to the activity, moreover as we are using the single activity architecture, we started the navigation
* graph from this BaseActivity.
*/
@AndroidEntryPoint
class BaseActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    internal lateinit var binding: ActivityBaseBinding
    private val dataViewModel: MovieSharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
         binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

}