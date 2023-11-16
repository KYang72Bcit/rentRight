package my.bcit.rentright.Views.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import my.bcit.rentright.Models.Listing.Listing
import my.bcit.rentright.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import my.bcit.rentright.Utils.CustomToast
import my.bcit.rentright.Views.Activity.ListingDetailActivity


class ListingDetailFragment : Fragment() {

    companion object {
        private const val ARG_LISTING = "listing"
        fun newInstance(listing: Listing): ListingDetailFragment {

            val fragment = ListingDetailFragment()
            fragment.arguments = Bundle().apply{
                val gson = Gson()
                val listingJson = gson.toJson(listing)
                putString(ARG_LISTING, listingJson)
            }
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_listing_detail, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listingJson = arguments?.getString(ARG_LISTING)
        val gson = Gson()
        val listingType = object : TypeToken<Listing>() {}.type
        val listing :Listing? = listingJson?.let { json ->
            gson.fromJson(json, listingType)}

        val rent: TextView = view.findViewById(R.id.listing_price)
        val title: TextView = view.findViewById(R.id.listing_address)
        val image: ImageView = view.findViewById(R.id.listing_image)
        val like: ImageButton = view.findViewById(R.id.listing_like_button)

        if (listing != null) {
            setViews(rent, title, like,  image, listing)
            setImage(image, listing.images[0])
        } else {
            CustomToast(requireContext(), "no listing info", "red")
        }

    }

    private fun setImage(image: ImageView, url: String ) {
        Glide.with(this)
            .load(url)
            .apply(RequestOptions().override(500, 500))
            .into(image)

    }
    private fun setViews(rent:TextView, title:TextView, like:ImageButton, image:ImageView, listing:Listing) {
        rent.text = "$${listing.rent}"
        title.text = listing.title
        like.setOnClickListener{
            if (like.tag == "not liked" ) {
                like.setImageResource(R.drawable.baseline_favorite_50)
                like.tag = "liked"
            } else {
                like.setImageResource(R.drawable.baseline_favorite_border_50)
                like.tag = "not liked"
            }
                //TODO: add call to ListModelView
        }
        image.setOnClickListener{
            var bundle = Bundle().apply {
                val gson = Gson()
                val listingsJson = gson.toJson(listing)
                putString("listing_json", listingsJson)
            }
            val intent = Intent(requireContext(), ListingDetailActivity::class.java)
            intent.putExtra("listing", bundle)
            startActivity(intent)


        }

    }
}
