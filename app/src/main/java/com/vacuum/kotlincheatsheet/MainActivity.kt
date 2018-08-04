package com.vacuum.kotlincheatsheet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ExpandableListView



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

    var mMenuAdapter: ExpandableListAdapter? = null
    var expandableList: ExpandableListView? = null
    var listDataHeader: List<ExpandedMenuModel>? = null
    var listDataChild: HashMap<ExpandedMenuModel, List<String>>? = null
    private fun navigathion_drawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        navigationView.setNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        prepareListData()
        mMenuAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList)
        expandableList!!.setAdapter(mMenuAdapter)
        expandableList!!.setOnChildClickListener(object : ExpandableListView.OnChildClickListener {
            override fun onChildClick(expandableListView: ExpandableListView, view: View, i: Int, i1: Int, l: Long): Boolean {
                //Log.d("DEBUG", "submenu item clicked");
                return false
            }
        })
        expandableList!!.setOnGroupClickListener(object : ExpandableListView.OnGroupClickListener {
            override fun onGroupClick(expandableListView: ExpandableListView, view: View, i: Int, l: Long): Boolean {
                //Log.d("DEBUG", "heading clicked");
                return false
            }
        })
    }

    private fun prepareListData() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()

        val item1 = ExpandedMenuModel()
        item1.iconName = "heading1"
        item1.iconImg = android.R.drawable.ic_delete
        // Adding data header
        listDataHeader.add(item1)


        val item2 = ExpandedMenuModel()
        item2.iconName = "heading2"
        item2.iconImg = android.R.drawable.ic_delete
        listDataHeader.add(item2)

        val item3 = ExpandedMenuModel()
        item3.iconName = "heading3"
        item3.iconImg = android.R.drawable.ic_delete
        listDataHeader.add(item3)

        // Adding child data
        val heading1 = ArrayList<String>()
        heading1.add("Submenu of item 1")

        val heading2 = ArrayList<String>()
        heading2.add("Submenu of item 2")
        heading2.add("Submenu of item 2")
        heading2.add("Submenu of item 2")

        listDataChild[listDataHeader.get(0)] = heading1// Header, Child data
        listDataChild[listDataHeader.get(1)] = heading2

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_one -> displayFromAsset("kotlincheatsheet.pdf")
            R.id.nav_item_two -> displayFromAsset("kotlin-full.pdf")
            R.id.nav_item_three -> displayFromAsset("Kotlin-Essentials.pdf")
            R.id.nav_item_four ->  displayFromAsset("Programming-Kotlin.pdf")
        }
        drawer.closeDrawer(GravityCompat.START)
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
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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
    private fun displayFromAsset(path:String = "kotlincheatsheet.pdf") {
        //kotlincheatsheet.pdf
        //kotlin-full.pdf
        pdfView!!.fromAsset(path)
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

