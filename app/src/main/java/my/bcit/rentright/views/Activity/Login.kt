package my.bcit.rentright.views.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import my.bcit.rentright.R
import my.bcit.rentright.ViewModels.UserViewModel
import my.bcit.rentright.Utils.Validator

class Login : AppCompatActivity() {

    private lateinit var displayEmail:TextInputLayout
    private lateinit var displayPassword: TextInputLayout

    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText

    private lateinit var signupButton: TextView
    private lateinit var loginButton: Button

    private lateinit var checkRememberMe: CheckBox
    private lateinit var displayForgetPassword: TextView
    val validator = Validator()
    private lateinit var sharedPreferences: SharedPreferences
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = getSharedPreferences("RentRight", MODE_PRIVATE);
        init()
        toSignup()
        logIn()
    }

    private fun init() {
        displayEmail = findViewById(R.id.displayEmail)
        displayPassword = findViewById(R.id.displayPassword)

        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)

        signupButton = findViewById(R.id.btnGoToSignUp)
        loginButton = findViewById(R.id.loginButton)

        checkRememberMe = findViewById(R.id.checkRememberMe)
        displayForgetPassword = findViewById(R.id.displayForgetPassword)
    }


    private fun toSignup()
    {
        signupButton.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
    }

    private fun logIn() {
        getTextWatcherReady()
        loginButton.setOnClickListener{
            if(validator.verifyIsNotEmpty(inputEmail, displayEmail)
                && validator.verifyIsNotEmpty(inputPassword, displayPassword)){
//                userViewModel.login(inputEmail, inputPassword, this, this )
                userViewModel.test()

//                val intent = Intent(this, HomePageActivity::class.java)
//                startActivity(intent)
//                finish()
            } else {
                return@setOnClickListener
            }
        }

    }





    private fun getTextWatcherReady() {
        inputEmail.addTextChangedListener(emailInputWatcher)
        inputPassword.addTextChangedListener(passwordInputWatcher)
    }

    private val emailInputWatcher:TextWatcher = object:TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            validator.verifyIsNotEmpty(inputEmail, displayEmail)
        }

    }

    private val passwordInputWatcher:TextWatcher = object:TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            validator.verifyIsNotEmpty(inputPassword, displayPassword)
        }

    }
}