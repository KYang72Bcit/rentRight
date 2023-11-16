package my.bcit.rentright.Models.Listing

import com.google.android.gms.maps.model.LatLng

data class Listing(
    val publisherID: String,
    val publisherName: String,
    val publisherEmail: String,
    val publisherPhone: String,
    val publisherFavorite: List<String>,
    val title: String,
    val rent: Int,
    val images: List<String>,
    val description: String,
    val category: String,
    val latLng: LatLng,
    val address: String,
    val city: String,
    val state: String,
    val zip: String,
    val id: String
)

