package com.vacuum.kotlincheatsheet

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import android.content.pm.PackageManager
import android.content.Intent
import android.content.ActivityNotFoundException
import android.graphics.Color
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var pdfView:PDFView? = null
    private val TAG = MainActivity::class.java!!.getSimpleName()
    private val REQUEST_CODE = 42
    val PERMISSION_CODE = 42042
    val SAMPLE_FILE = "sample.pdf"
    val READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"
    var uri: Uri? = null
    var pageNumber: Int? = 0
    var pdfFileName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pdfView  = findViewById(R.id.pdfView)
        btn_view.setOnClickListener(View.OnClickListener {
            pickFile()
        })
    }




    fun pickFile() {
        val permissionCheck = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(READ_EXTERNAL_STORAGE),
                    PERMISSION_CODE
            )
            return
        }
        displayFromAsset()
        //launchPicker()
    }
    private fun displayFromAsset() {
        pdfView!!.fromAsset("sample.pdf")
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .scrollHandle(DefaultScrollHandle(this))
                .spacing(10) // in dp
                .load()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayFromAsset()
            }
        }
    }
}

