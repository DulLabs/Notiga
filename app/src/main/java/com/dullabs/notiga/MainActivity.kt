package com.dullabs.notiga

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dullabs.notiga.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding;
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
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val sideNav = findViewById<NavigationView>(R.id.sideNavView)
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.inboxFragment, R.id.batchFragment, R.id.configureFragment),
            findViewById<DrawerLayout>(R.id.drawerLayout)
        )

        val navGraphIds =
            listOf(R.navigation.inbox, R.navigation.batch, R.navigation.configure)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.navHostFragment,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            sideNav.setupWithNavController(navController)
            topAppBar.setupWithNavController(navController, appBarConfiguration)
        })

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}