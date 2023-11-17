package my.bcit.rentright.Models.User

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("username") var username: String,
    @SerializedName("email") var useremail: String,
    @SerializedName("_id") val id:String,
    @SerializedName("phone") val phone:String,
    @SerializedName("favorites") val favorites : List<String>,
) :  Serializable

