package my.bcit.rentright.views.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import my.bcit.rentright.R
import my.bcit.rentright.databinding.ActivityLandingBinding

class Landing : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getStartedButton: Button = findViewById(R.id.getStartedButton)
        getStartedButton.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }
        }
    }




