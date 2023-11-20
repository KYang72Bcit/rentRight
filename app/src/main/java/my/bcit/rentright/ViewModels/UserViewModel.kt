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

                        getReady.goToHomePage(context, activity)
                        CustomToast(context, "Login Successful", "Green")

                    }
                }
                else {
                    Log.e("code", response.code().toString())
                    Log.e("Message :",  response.body().toString())
                    CustomToast(context, response.body().toString(), "Red")

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error on Login", t.message.toString())
                CustomToast(context, "Network is not available", "Red")


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
                    CustomToast(context, "Login Successful", "Green")

                    Handler(Looper.getMainLooper()).postDelayed({
                        getReady.goToHomePage(context,activity) //GoTo Page Login
                    }, 4500)
                    //
                }else{
                    CustomToast(context, response.body().toString(), "Red")
                    Log.i("response code", response.code().toString())
                    Log.i("error body", response.body().toString())

                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                CustomToast(context,"Network is not available", "Red")

            }
        })
    }


     fun logout() {
         service?.logout()?.enqueue(object : Callback<JsonObject> {
             override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                 if (response.code() == 200) {
                     currentUser.value = null
                     Log.i("operation", "logged out")
                     Log.i("user", currentUser.value.toString())


                 }
             }

             override fun onFailure(call: Call<JsonObject>, t: Throwable) {

             }
         })

         }

    fun 


}