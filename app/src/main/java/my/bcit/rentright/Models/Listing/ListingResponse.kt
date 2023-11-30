package my.bcit.rentright.Models.Listing

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import my.bcit.rentright.Models.User.User


data class ListingResponse(
    @SerializedName("_id")
    @Expose
    var id: String = "",

    @SerializedName("publisher")
    @Expose
    var publisher: User? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("images")
    @Expose
    var images: List<String>? = null,

    @SerializedName("rent")
    @Expose
    var rent: Int = 0,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("location")
    @Expose
    var location: Location? = null,

    @SerializedName("category")
    @Expose
    var category: String? = null,



    ) {
    data class Location(
        @SerializedName("lat")
        @Expose
        var lat: Double? = null,

        @SerializedName("lon")
        @Expose
        var lon: Double? = null,

        @SerializedName("address")
        @Expose
        var address: String? = null,

        @SerializedName("city")
        @Expose
        var city: String? = null,

        @SerializedName("state")
        @Expose
        var state: String? = null,

        @SerializedName("zip")
        @Expose
        var zip: String? = null
    )
}


fun ListingResponse.toListing(): Listing = Listing(
    publisherID = publisher?.id?:"",
    publisherName = publisher?.username?:"",
    publisherEmail = publisher?.useremail?:"",
    publisherPhone = publisher?.phone?:"",
    publisherFavorite = publisher?.favorites?: emptyList(),

    title = title ?: "",
    id = id ?:"",
    images = images ?: emptyList(),
    description = description ?: "",
    category = category ?: "",
    rent = rent,
    latLng = LatLng(
        location?.lat ?: 0.0,
        location?.lon ?: 0.0
    ),

    address = location?.address ?: "",
    city = location?.city ?: "",
    state = location?.state ?: "",
    zip = location?.zip ?: ""
)


