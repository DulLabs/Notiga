package com.dullabs.notiga

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dullabs.notiga.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        if (savedInstanceState == null) {
            setupBottomNavBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavBar()
    }

    private fun setupBottomNavBar() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.inboxFragment, R.id.batchFragment, R.id.configureFragment),
            mainBinding.drawerLayout
        )

        val navGraphIds =
            listOf(R.navigation.inbox, R.navigation.batch, R.navigation.configure)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = mainBinding.bottomNavView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.navHostFragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            mainBinding.sideNavView.setupWithNavController(navController)
            mainBinding.topAppBar.setupWithNavController(navController, appBarConfiguration)
        })

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    fun hideBottomControls() {
        hideBottomAppBar()
        hideBottomFab()
    }

    fun showBottomControls() {
        showBottomAppBar()
        showBottomFab()
    }

    private fun hideBottomAppBar() {
        mainBinding.bottomAppBar.visibility = View.GONE
    }

    private fun showBottomAppBar() {
        mainBinding.bottomAppBar.visibility = View.VISIBLE
    }

    private fun hideBottomFab() {
        mainBinding.bottomFab.visibility = View.GONE
    }

    private fun showBottomFab() {
        mainBinding.bottomFab.visibility = View.VISIBLE
    }

    fun getMainBinding(): ActivityMainBinding {
        return mainBinding
    }

    fun setFABOnClickListener(onClickAction: () -> Unit) {
        mainBinding.bottomFab.setOnClickListener { view ->
            onClickAction()
        }
    }

}