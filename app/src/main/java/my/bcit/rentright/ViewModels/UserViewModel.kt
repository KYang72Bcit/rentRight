package my.bcit.rentright.ViewModels

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import my.bcit.rentright.Network.RentRightRetrofit
import my.bcit.rentright.Utils.*
import my.bcit.rentright.Network.UserAPI
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonPrimitive
import kotlinx.coroutines.launch
import my.bcit.rentright.Models.User.User


class UserViewModel: ViewModel() {
    private var retrofit: Retrofit? = RentRightRetrofit.getInstance()
    private val service: UserAPI? = retrofit?.create(UserAPI::class.java)
    private val getReady = GetReady()
    val currentUser = MutableLiveData<User?>()

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            val user = getCurrentUser()
            currentUser.value = user
        }
    }

    private suspend fun getCurrentUser(): User? {

        return try {
            val response = service?.getCurrent()
            if (response?.isSuccessful == true && response.body()?.success == true) {
                Log.i("getCurrentUser", response.body()!!.user.toString())
                response.body()?.user
            } else {

                null
            }
        } catch (e: Exception) {

            null
        }
    }

    fun login(email: TextInputEditText, pwd:TextInputEditText, context:Context, activity:Activity) {

        val userJson = JsonObject().apply {
            addProperty("email", email.text.toString().trim())
            addProperty("password", pwd.text.toString().trim())
        }

        service?.login(userJson)?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 200) {

                    val body = response.body()?.toString()
                    Log.i("on log in response", body.toString())
                    if (!body.isNullOrEmpty()) {
                        CustomToast(context, "Login Successful", "GREEN").show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            getReady.goToHomePage(context, activity) //GoTo Page Login
                        }, 7000)
                        getReady.goToHomePage(context, activity)
                    }
                }
                else {
                    Log.e("code", response.code().toString())
                    Log.e("Message :",  response.body().toString())
                    CustomToast(context, "Check Password or Email", "RED").show()

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error on Login", t.message.toString())
                CustomToast(context, "Network is not available", "RED").show()


            }
        })
    }

    fun register(name: TextInputEditText, email: TextInputEditText, pwd:TextInputEditText, context:Context, activity : Activity){
        val map: HashMap<String, String> = HashMap()
        map["username"] = name.text.toString()
        map["email"] = email.text.toString()
        map["password"] = pwd.text.toString()
        service?.register(map)?.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                if (response.code()==200){
                    CustomToast(context, "Sign up Successful", "GREEN").show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        getReady.goToHomePage(context,activity) //GoTo Page Login
                    }, 4500)
                    //
                }else{
                    CustomToast(context, response.body().toString(), "RED").show()
                    Log.i("response code", response.code().toString())
                    Log.i("error body", response.body().toString())

                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                CustomToast(context,"Network is not available", "RED").show()

            }
        })
    }


     fun logout() {
         service?.logout()?.enqueue(object : Callback<JsonObject> {
             override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                 if (response.code() == 200) {
                     currentUser.value = null

                     Log.i("user", currentUser.value.toString())


                 }
             }

             override fun onFailure(call: Call<JsonObject>, t: Throwable) {

             }
         })

         }

     fun updateUser(listingID:String) {

        viewModelScope.launch {
            val favoritesObject = JsonObject().apply {
                add("favorites", JsonPrimitive(listingID))
            }

            val requestObject = JsonObject().apply {
                add("\$push", favoritesObject)
            }


            try {
                val response = service?.updateUser(requestObject)
                Log.i("updateUserCode", response!!.code().toString())
                Log.i("uptaedUser", response!!.body().toString())
//                if (response!!.isSuccessful && response.body()?.success == true){
//                    Log.i("UpdateUser", response.body()!!.user.toString())
//                    currentUser.value = response.body()!!.user
//                }
            } catch (e:Exception) {
                Log.e("error in updateUser", e.message.toString())

            }

//            service?.updateUser(requestBody)?.let{ response ->
//                if(response.isSuccessful){
//
//                    Log.i("updateUserFavorite", "success")
//                    currentUser.value =
//                } else {
//                    Log.i("updateUserFavorite", "failed")
//                }
//            }
            }
        }

    }


