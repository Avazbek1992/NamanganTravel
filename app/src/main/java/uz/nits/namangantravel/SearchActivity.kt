package uz.nits.namangantravel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import uz.nits.namangantravel.databinding.SearchActivityLayoutBinding
import uz.nits.namangantravel.utils.DataStorage

class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var binding: SearchActivityLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val servesName = intent.getStringExtra(DataStorage.KEY_EXTRA)
        supportActionBar?.title = servesName
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.serach_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed();
        }
        return true
    }
}