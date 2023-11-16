package my.bcit.rentright.Utils

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Validator {

    fun verifyIsNotEmpty(input: TextInputEditText, display: TextInputLayout): Boolean {
        val value: String = input.text.toString().trim()
        if (value.isEmpty()) {
            display.error = "Must Not Be Empty"
            return false
        } 
        display.error = null
        display.isErrorEnabled = false
        return true
        
    }

    fun verifyEmail (inputEmail: TextInputEditText, displayEmail: TextInputLayout): Boolean {
        val email = inputEmail.text.toString().trim()
        val regexPattern = Regex("^[A-Za-z0-9+_.-]+@(.+)\$")
        if (!regexPattern.matches(email)) {
            displayEmail.error = "Email Address is not valid"
            displayEmail.isErrorEnabled = true
            return false
        }
        displayEmail.error = null
        displayEmail.isErrorEnabled = false
        return true
        
    }

    fun verifyPasswordEqual(inputPassword: TextInputEditText, inputConfirmPassword: TextInputEditText, displayConfirmPassword: TextInputLayout): Boolean{
        val pwd = inputPassword.text.toString().trim()
        val confirmPwd = inputConfirmPassword.text.toString().trim()

        if(pwd!=confirmPwd){
            displayConfirmPassword.error = "Passwords you entered don't match"
            return false
        }
        displayConfirmPassword.error = null
        displayConfirmPassword.isErrorEnabled = false
        return true 

    }
    fun verifyPasswordLength(inputPassword: TextInputEditText, displayPassword : TextInputLayout): Boolean {
        val pwd = inputPassword.text.toString().trim()
        if (pwd.length < 7) {
            displayPassword.error = "Passwords should be more than 7 characters"
            return false
        }
        return true
    }

}