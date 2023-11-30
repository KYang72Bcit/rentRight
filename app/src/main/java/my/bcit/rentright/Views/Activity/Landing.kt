package my.bcit.rentright.Views.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import my.bcit.rentright.Models.Listing.ListingResponse
import my.bcit.rentright.Utils.CustomToast
import my.bcit.rentright.ViewModels.ListingViewModel
import my.bcit.rentright.databinding.ActivityLandingBinding

class Landing : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding
    private val listingViewModel: ListingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe the allListings LiveData from the ListingViewModel.
        listingViewModel.allListings.observe(this) { listings ->

            if (listings != null && listings.isNotEmpty()) {
                navigateToHome(listings)
            } else {
                CustomToast(this, "Sorry, Something Went Wrong!", "RED").show()
            }
            listings?.let{
                navigateToHome()
                //navigateToHome()
            } ?:run {
                CustomToast(this, "Sorry, Something Goes Wrong!", "RED").show()
            }

        }

        }


    private fun navigateToHome() {
        intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
        finish()
     }



    private fun navigateToHome(listings: List<ListingResponse>) {
        val gson = Gson()
        val listingsJson = gson.toJson(listings)
        val bundle = Bundle().apply {
            putString("listings_json", listingsJson)
        }
        val intent = Intent(this, HomePageActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
        finish() // End this activity
    }
}


