package com.vacuum.kotlincheatsheet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var pdfView:PDFView? = null
    val PERMISSION_CODE = 42042
    val READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE"
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pdfView  = findViewById(R.id.pdfView)

        navigathion_drawer()
        pickFile()
    }

    private fun navigathion_drawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> Toast.makeText(this, "Clicked item one", Toast.LENGTH_SHORT).show()
            R.id.nav_item_two -> Toast.makeText(this, "Clicked item two", Toast.LENGTH_SHORT).show()
            R.id.nav_item_three -> Toast.makeText(this, "Clicked item three", Toast.LENGTH_SHORT).show()
            R.id.nav_item_four -> Toast.makeText(this, "Clicked item four", Toast.LENGTH_SHORT).show()
        }
        return true
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    fun pickFile() {
        val permissionCheck = ContextCompat.checkSelfPermission(this,
                READ_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(READ_EXTERNAL_STORAGE),
                    PERMISSION_CODE)
            return
        }
        displayFromAsset()
    }
    private fun displayFromAsset() {
        //kotlincheatsheet.pdf
        //kotlin-full.pdf
        pdfView!!.fromAsset("kotlin-full.pdf")
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

