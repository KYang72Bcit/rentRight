package my.bcit.rentright.Views.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import my.bcit.rentright.R
import my.bcit.rentright.Utils.Validator

class Signup : AppCompatActivity() {

    private lateinit var displayName: TextInputLayout
    private lateinit var displayEmail: TextInputLayout
    private lateinit var displayPassword: TextInputLayout
    private lateinit var displayConfirmPassword: TextInputLayout

    private lateinit var inputName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputConfirmPassword: TextInputEditText

    private lateinit var registerButton: Button
    private lateinit var loginTxt: TextView


    var validator = Validator()

////only checks the fields for now without creating the user ////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        init()
        toLogin()
        toSignup()
    }

    private fun init() {

        displayName = findViewById(R.id.displayName)
        displayEmail = findViewById(R.id.displayEmail)
        displayPassword = findViewById(R.id.displayPassword)
        displayConfirmPassword = findViewById(R.id.displayConfirmPassword)

        inputName = findViewById(R.id.inputName)
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword)

        registerButton= findViewById(R.id.registerButton)
        loginTxt = findViewById(R.id.loginTxt)


    }
    private fun toLogin()
    {
        loginTxt.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun toSignup(){
        getTextWatcherReady()
        registerButton.setOnClickListener{
            if(isAllFieldsValid()){
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
            } else {
                return@setOnClickListener
            }
        }

    }
    private fun isAllFieldsValid(): Boolean {
        val checkList :List<Boolean> = listOf(
            validator.verifyIsNotEmpty(inputName, displayName),
            validator.verifyIsNotEmpty(inputEmail, displayEmail),
            validator.verifyIsNotEmpty(inputPassword, displayPassword),
            validator.verifyIsNotEmpty(inputConfirmPassword, displayConfirmPassword),
            validator.verifyEmail(inputEmail, displayEmail),
            validator.verifyPasswordEqual(inputPassword, inputConfirmPassword, displayConfirmPassword))
        return checkList.all { it }

    }

    private fun getTextWatcherReady() {
        inputName.addTextChangedListener(nameInputWatcher)
        inputEmail.addTextChangedListener(emailInputWatcher)
        inputPassword.addTextChangedListener(passwordInputWatcher)
        inputConfirmPassword.addTextChangedListener(confirmPasswordInputWatcher)
    }

    private val nameInputWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            validator.verifyIsNotEmpty(inputName, displayName)

        }
    }
    private val emailInputWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            validator.verifyIsNotEmpty(inputEmail, displayEmail)
        }
    }
    private val passwordInputWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            validator.verifyIsNotEmpty(inputPassword, displayPassword)
        }
    }
    private val confirmPasswordInputWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            validator.verifyIsNotEmpty(inputConfirmPassword, displayConfirmPassword)
        }
    }
}