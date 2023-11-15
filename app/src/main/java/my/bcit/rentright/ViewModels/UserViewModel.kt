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
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import my.bcit.rentright.Network.RentRightRetrofit
import my.bcit.rentright.Utils.*
import my.bcit.rentright.Network.UserAPI
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.bcit.rentright.Models.User


class UserViewModel: ViewModel() {
    private var retrofit: Retrofit? = RentRightRetrofit.getInstance()
    private val service: UserAPI? = retrofit?.create(UserAPI::class.java)
    private val statusMessage = MutableLiveData<String>()
    private val getReady = GetReady()
    val currentUser = MutableLiveData<User?>()
    private lateinit var sharedPreferences: SharedPreferences

    init {
        checkCurrentUser()
    }

    // 刷新当前用户的状态
    fun checkCurrentUser() {
        viewModelScope.launch {
            val user = getCurrentUser()
            currentUser.postValue(user)
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
                    if (!body.isNullOrEmpty()) {
                        val userData = JsonParser.parseString(body).asJsonObject


                        storeUserData(userData, context)

                        getReady.goToHomePage(context, activity)
                        statusMessage.value = "Sign in successful"

                    }
                }
                else {
                    Log.e("code", response.code().toString())
                    Log.e("Message :",  response.body().toString())

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error on Login", t.message.toString())


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

                    Handler(Looper.getMainLooper()).postDelayed({
                        getReady.goToHomePage(context,activity) //GoTo Page Login
                    }, 4500)
                    //
                }else{

                    Log.i("response code", response.code().toString())
                    Log.i("error body", response.body().toString())

                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

            }
        })
    }

    suspend fun getCurrentUser(): User? {
        return try {
            val response = service?.getCurrent()
            if (response?.isSuccessful == true) {
                response.body()
            } else {

                null
            }
        } catch (e: Exception) {

            null
        }
    }

     fun logout() {
         service?.logout()?.enqueue(object : Callback<JsonObject> {
             override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                 if (response.code() == 200) {


                 } else {

                 }

             }

             override fun onFailure(call: Call<JsonObject>, t: Throwable) {


             }
         })

         }




    private fun storeUserData(userData:JsonObject, context:Context)  {
        if (userData.get("success").asBoolean){
            val user = userData.get("user").asJsonObject
            var userID = user.asJsonObject.get("_id").asString
            val userName =user.asJsonObject.get("username").asString
            val userEmail = user.asJsonObject.get("email").asString
            val userPhone = user.asJsonObject.get("phone")?.asString
            sharedPreferences = context.getSharedPreferences(
                "Rentright",
                AppCompatActivity.MODE_PRIVATE
            );

            sharedPreferences.edit().apply{
                putString("userName", userName)
                putString("userEmail", userEmail)
                putString("userPhone", userPhone)
                putString("userID", userID)

            }.apply()

        }
    }




}