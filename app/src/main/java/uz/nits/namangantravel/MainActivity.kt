package uz.nits.namangantravel

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import uz.nits.namangantravel.databinding.ActivityMainBinding
import uz.nits.namangantravel.databinding.NavHeaderMainBinding
import uz.nits.namangantravel.utils.DataStorage

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStorage: DataStorage
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStorage = DataStorage()
        if (dataStorage.readSharedPreferences(this, dataStorage.profileEmail) == ""
            && dataStorage.readSharedPreferences(this, dataStorage.profilePassword) == ""
        ) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        drawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        val viewHeader = navView.getHeaderView(0)
        val navHeaderBinding = NavHeaderMainBinding.bind(viewHeader)
        val email = dataStorage.readSharedPreferences(this, dataStorage.profileEmail)
        navHeaderBinding.navHeaderEmail.text = email

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home/*, R.id.nav_gallery, R.id.nav_slideshow*/
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                dataStorage.saveSharedPreferences(this, "", "")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            android.R.id.home -> {
                drawerLayout.open()
            }
        }
        return true
    }
}