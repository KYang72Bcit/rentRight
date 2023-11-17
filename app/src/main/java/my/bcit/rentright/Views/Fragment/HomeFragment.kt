package my.bcit.rentright.Views.Fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import my.bcit.rentright.R
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import my.bcit.rentright.Models.Listing.Listing
import my.bcit.rentright.Models.Listing.ListingResponse
import my.bcit.rentright.Models.Listing.toListing
import my.bcit.rentright.ViewModels.ListingViewModel
import my.bcit.rentright.Views.Activity.place.Place
import my.bcit.rentright.Views.Activity.place.PlacesReader


class HomeFragment : Fragment() {

    private var listings: List<Listing> = emptyList()
    private val listingViewModel: ListingViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchFragment = SearchComponentFragment()
        childFragmentManager.beginTransaction()
            .add(R.id.search_fragment_container, searchFragment)
            .commit()


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            addMarkers(googleMap, listings)
            setMapListener(googleMap)
        }


        listingViewModel.allListings.observe(viewLifecycleOwner) { searchResult ->
            searchResult?.let {results ->
                run {
                    listings = results.map { it.toListing() }
                }
                mapFragment?.getMapAsync { googleMap ->
                    googleMap.clear()
                    addMarkers(googleMap, listings)

                    val bounds = LatLngBounds.builder()
                    listings.forEach { bounds.include(it.latLng) }
                    if (listings.isNotEmpty()) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
                    }
                }

            }
        }

        listingViewModel.closeDetailEvent.observe(viewLifecycleOwner) { shouldClose ->
            if (shouldClose == true) {
                closeListingDetailFragment()
                listingViewModel.onCloseDetailRequestedComplete()
            }
        }

    }


    private fun addMarkers(googleMap: GoogleMap, listings: List<Listing>) {
        listings.forEach { listing ->
            googleMap.addMarker(
                MarkerOptions()
                    .position(listing.latLng)
            )
        }
    }

    private fun findListingByMarker(marker: Marker):Listing?{
        return listings.find { it.latLng == marker.position }

    }



    private fun showListingDetailFragment(listing: Listing) {
        val listingDetailFragment = ListingDetailFragment.newInstance(listing)
        childFragmentManager.beginTransaction()
            .replace(R.id.listing_detail_container, listingDetailFragment)
            .addToBackStack(null)
            .commit()
    }
     private fun closeListingDetailFragment() {
        val fragment = childFragmentManager.findFragmentById(R.id.listing_detail_container)
        if (fragment != null) {
            childFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
    }

    private fun setMapListener(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener { marker ->
            val listing = findListingByMarker(marker)
            listing?.let{
                showListingDetailFragment(it)
            }
            true

        }
        googleMap.setOnMapClickListener {
            closeListingDetailFragment()
        }

        googleMap.setOnMapLoadedCallback {
            val bounds = LatLngBounds.builder()
            listings.forEach { bounds.include(it.latLng) }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
        }
    }
}