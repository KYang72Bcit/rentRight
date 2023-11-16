package my.bcit.rentright.Views.Activity


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import my.bcit.rentright.Models.Listing.Listing
import my.bcit.rentright.R


class ListingDetailActivity : AppCompatActivity() {
    private lateinit var listing :Listing
    private lateinit var rent : TextView
    private lateinit var address : TextView
    private lateinit var city : TextView
    private lateinit var state : TextView
    private lateinit var zipcode : TextView
    private lateinit var description: TextView
    private lateinit var image : ImageView
    private lateinit var backBtn: ImageButton
    private lateinit var callBtn: Button
    private lateinit var emailBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_detail)

        init()
        val bundle = intent.extras?.getBundle("listing")

        val listingJson = bundle?.getString("listing_json")
        val gson = Gson()
        val listingType = object : TypeToken<Listing>() {}.type
        listing = listingJson?.let { json ->
            gson.fromJson(json, listingType)}!!

       setViews(rent, address, city, state, zipcode, description, image, listing)
    }


    private fun init() {
        rent = findViewById(R.id.rentTextView)
        address =findViewById(R.id.addressTextView)
        city  = findViewById(R.id.cityTextView)
        state = findViewById(R.id.stateTextView)
        zipcode = findViewById(R.id.zipcodeTextView)
        description = findViewById(R.id.descriptionTextView)
        image  = findViewById(R.id.imageView)
        backBtn = findViewById(R.id.backButton)
        callBtn = findViewById(R.id.callButton)
        emailBtn = findViewById(R.id.emailButton)
    }
    private fun setViews(rent: TextView, address: TextView,
                         city: TextView, state: TextView,
                         zipcode: TextView, description: TextView, image: ImageView, listing:Listing){
        rent.text = "$${listing.rent}"
        address.text = listing.address
        city.text = listing.city
        state.text = listing.state
        zipcode.text = listing.zip
        description.text = listing.description
        setImage(image, listing.images[0])
        setGoBackBtn(backBtn)
        setCallBtn(callBtn)
        setSentEmailBtn(emailBtn)

    }

    private fun setGoBackBtn(backBtn:ImageButton){
        backBtn.setOnClickListener{
            intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setSentEmailBtn(emailBtn : Button) {
        emailBtn.setOnClickListener{
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(listing.publisherEmail))
            putExtra(Intent.EXTRA_SUBJECT, "View your property at ${listing.address}`request")
            putExtra(Intent.EXTRA_TEXT, "content")
        }
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        }
    }
    }

    private fun setCallBtn(callBtn: Button) {
        callBtn.setOnClickListener{
            val phone = listing.publisherPhone
            val intent = Intent(Intent.ACTION_DIAL).apply{
                data = Uri.parse("tel:$phone")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }


    private fun setImage(image: ImageView, url: String) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().override(500, 500))
            .into(image)

    }
}