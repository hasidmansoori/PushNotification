package com.example.pushnotification.ui

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pushnotification.R

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.pushnotification.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase Auth
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        } else {
            firebaseUser = user
            setupUserInfo()
        }

        binding.btnPdf.setOnClickListener {
            startActivity(Intent(this, PdfViewerActivity::class.java))
        }

        binding.btnImage.setOnClickListener {
            startActivity(Intent(this, ImageCaptureActivity::class.java))
        }

        // Notification Preference Switch
        val sharedPref = getSharedPreferences("prefs", MODE_PRIVATE)
        binding.switchNotifications.isChecked = sharedPref.getBoolean("notifications_enabled", true)

        binding.switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("notifications_enabled", isChecked).apply()
            Toast.makeText(this, if (isChecked) "Notifications Enabled" else "Notifications Disabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupUserInfo() {
        binding.tvUserName.text = firebaseUser.displayName
        binding.tvUserEmail.text = firebaseUser.email
        Glide.with(this)
            .load(firebaseUser.photoUrl)
            .into(binding.imgProfile)
    }
}