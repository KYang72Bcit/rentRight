package my.bcit.rentright.Views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import my.bcit.rentright.Models.Listing.ListingResponse
import my.bcit.rentright.R
import my.bcit.rentright.Utils.CustomToast
import my.bcit.rentright.ViewModels.ListingViewModel
import my.bcit.rentright.ViewModels.UserViewModel
import my.bcit.rentright.Views.Fragment.FavFragment
import my.bcit.rentright.Views.Fragment.HomeFragment
import my.bcit.rentright.Views.Fragment.ProfileFragment
import my.bcit.rentright.Views.Fragment.SearchFragment

class HomePageActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private val listingViewModel: ListingViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val bundle = intent.extras
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        listingViewModel.allListings.observe(this) { listings ->
            listings?.let {
                updateUIWithListings(it)
            } ?: run {
                CustomToast(this, "Sorry, Something Goes Wrong!", "RED").show()
            }
        }
        setupBottomNav()


        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()
            .apply{
                arguments = bundle
            }).commit();

    }

    private fun updateUIWithListings(listings: List<ListingResponse>) {
        val bundle = Bundle().apply {
            val gson = Gson()
            val listingsJson = gson.toJson(listings)
            putString("listings_json", listingsJson)
        }

        val transaction = supportFragmentManager.beginTransaction()
        val homeFragment = HomeFragment().apply {
            arguments = bundle
        }
        transaction.replace(R.id.container, homeFragment)
        transaction.commit()
    }

    private fun setupBottomNav() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> switchToFragment(HomeFragment())
                R.id.nav_refresh -> listingViewModel.refreshListings()
                R.id.nav_search -> switchToFragment(SearchFragment())
                R.id.nav_fav, R.id.nav_profile -> {
                    observeUserForLogin(item)
                }
            }
            true
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this@HomePageActivity, Login::class.java)
        startActivity(intent)
    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun observeUserForLogin(item: MenuItem) {
        userViewModel.currentUser.observe(this) { user ->
            if (user == null) {

                navigateToLogin()
                userViewModel.currentUser.removeObservers(this)
            } else {
                Log.i("inHomeActivity", user.toString())
                val fragment = if (item.itemId == R.id.nav_fav) FavFragment() else ProfileFragment()
                switchToFragment(fragment)
                userViewModel.currentUser.removeObservers(this)
            }
        }
    }


    }
