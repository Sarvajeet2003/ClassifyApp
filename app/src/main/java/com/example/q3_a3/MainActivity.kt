package com.example.q3_a3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private external fun initializeNNAPI(): Boolean
    private external fun runInferenceWithNNAPI(): String
    private lateinit var galleryButton: Button
    private lateinit var imageView: ImageView

    private val GALLERY_REQUEST_CODE = 101
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        System.loadLibrary("native-lib")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById(R.id.sample_text)

        // Initialize NNAPI
        val initialized = initializeNNAPI()
        if (initialized) {
            // Run inference with NNAPI
            val inferenceResult = runInferenceWithNNAPI()
            tv.text = inferenceResult
        } else {
            tv.text = "NNAPI initialization failed"
        }
        // Initialize buttons and imageView
        galleryButton = findViewById(R.id.galleryButton)
        imageView = findViewById(R.id.imageView)

        // Set click listeners for buttons
        galleryButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Allow multiple image selection
            }
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }
    }

    // onRequestPermissionsResult and onActivityResult methods remain the same

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    if (data != null) {
                        val clipData = data.clipData
                        if (clipData != null) {
                            for (i in 0 until clipData.itemCount) {
                                val uri = clipData.getItemAt(i).uri
                                println(uri)
                                // Handle each selected image uri
                                // For demonstration, you can display each image in ImageView
                                // You may need to resize or handle images differently depending on your requirement
                                imageView.setImageURI(uri)
                            }
                        } else {
                            val uri = data.data
                            // Handle the single selected image uri
                            imageView.setImageURI(uri)
                        }
                    }
                }
            }
        }
    }
}