package com.example.ass3q2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var galleryButton: Button
    private lateinit var imageView: ImageView

    private val GALLERY_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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