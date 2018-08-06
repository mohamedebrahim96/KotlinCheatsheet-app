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
import android.view.Menu
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
    private var mMenuAdapter: ExpandableListAdapter? = null
    private var expandableList: ExpandableListView? = null
    private var listDataHeader: MutableList<ExpandedMenuModel>? = null
    private var listDataChild: HashMap<ExpandedMenuModel,List<Item>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pdfView  = findViewById(R.id.pdfView)

        navigathion_drawer()
        pickFile()
    }


    private fun navigathion_drawer() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        navigationView.setNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)
        expandableList =  findViewById(R.id.navigationmenu)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        prepareListData()
        mMenuAdapter = ExpandableListAdapter(this, pdfView!!, listDataHeader!!, listDataChild!!, expandableList!!)
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
        val cheatsheet = ArrayList<Item>()
        val ful_documantation = ArrayList<Item>()
        val Essentials = ArrayList<Item>()
        val Programming_Kotlin = ArrayList<Item>()

        listDataChild = HashMap()

        val item1 = ExpandedMenuModel()
        item1.iconName = "Cheat Sheet"
        item1.iconImg = R.drawable.ic_kotlin
        // Adding data header
        listDataHeader!!.add(item1)


        val item2 = ExpandedMenuModel()
        item2.iconName = "Full Documentation"
        item2.iconImg = R.drawable.ic_kotlin
        listDataHeader!!.add(item2)

        val item77 = ExpandedMenuModel()
        item77.iconName = "Kotlin Essentials"
        item77.iconImg = R.drawable.ic_kotlin
        listDataHeader!!.add(item77)

        val item4 = ExpandedMenuModel()
        item4.iconName = "Programming Kotlin"
        item4.iconImg = R.drawable.ic_kotlin
        listDataHeader!!.add(item4)
//=====================================================
//=====================================================
//=====================================================
        // Adding child data
        val heading1_1 = Item()
        heading1_1.page_numer = 0
        heading1_1.title = "Chapter 1"
        heading1_1.book_name = "kotlincheatsheet.pdf"
        cheatsheet!!.add(heading1_1)

        val heading1_2 = Item()
        heading1_2.page_numer = 1
        heading1_2.title = "Chapter 2"
        heading1_2.book_name = "kotlincheatsheet.pdf"
        cheatsheet!!.add(heading1_2)

        val heading1_3 = Item()
        heading1_3.page_numer = 2
        heading1_3.title = "Chapter 3"
        heading1_3.book_name = "kotlincheatsheet.pdf"
        cheatsheet!!.add(heading1_3)
///=============================================================
///=============================================================
///=============================================================
///=============================================================
///=============================================================

        // Adding child data
        val heading2_1 = Item()
        heading2_1.page_numer = 4
        heading2_1.title = "Overview"
        heading2_1.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_1)

        val heading2_2 = Item()
        heading2_2.page_numer = 23
        heading2_2.title = "Getting Started"
        heading2_2.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_2)

        val heading2_3 = Item()
        heading2_3.page_numer = 42
        heading2_3.title = "Basics"
        heading2_3.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_3)

        val heading2_4 = Item()
        heading2_4.page_numer = 52
        heading2_4.title = "Classes and Objects"
        heading2_4.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_4)


        val heading2_5 = Item()
        heading2_5.page_numer = 90
        heading2_5.title = "Functions and Lambdas"
        heading2_5.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_5)

        val heading2_6 = Item()
        heading2_6.page_numer = 108
        heading2_6.title = "Other"
        heading2_6.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_6)

        val heading2_7 = Item()
        heading2_7.page_numer = 147
        heading2_7.title = "Reference"
        heading2_7.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_7)

        val heading2_8 = Item()
        heading2_8.page_numer = 194
        heading2_8.title = "Tools"
        heading2_8.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_8)

        val heading2_9 = Item()
        heading2_9.page_numer = 223
        heading2_9.title = "FAQ"
        heading2_9.book_name = "kotlin-full.pdf"
        ful_documantation.add(heading2_9)
        ///=============================================================================
        ///=============================================================================
        ///=============================================================================
        ///=============================================================================
        ///=============================================================================

        // Adding child data
        val heading3_1 = Item()
        heading3_1.page_numer = 26
        heading3_1.title = "Chapter 1"
        heading3_1.book_name = "Kotlin-Essentials.pdf"
        Essentials!!.add(heading3_1)

        val heading3_2 = Item()
        heading3_2.page_numer = 28
        heading3_2.title = "Chapter 2"
        heading3_2.book_name = "Kotlin-Essentials.pdf"
        Essentials!!.add(heading3_2)

        val heading3_3 = Item()
        heading3_3.page_numer = 36
        heading3_3.title = "Chapter 3"
        heading3_3.book_name = "Kotlin-Essentials.pdf"
        Essentials!!.add(heading3_3)

        val heading3_4 = Item()
        heading3_4.page_numer = 48
        heading3_4.title = "Chapter 4"
        heading3_4.book_name = "Kotlin-Essentials.pdf"
        Essentials!!.add(heading3_4)

        val heading3_5 = Item()
        heading3_5.page_numer = 56
        heading3_5.title = "Chapter 5"
        heading3_5.book_name = "Kotlin-Essentials.pdf"
        Essentials!!.add(heading3_5)

        val heading3_6 = Item()
        heading3_6.page_numer = 66
        heading3_6.title = "Chapter 6"
        heading3_6.book_name = "Kotlin-Essentials.pdf"
        Essentials!!.add(heading3_6)

        val heading3_7 = Item()
        heading3_7.page_numer = 72
        heading3_7.title = "Chapter 7"
        heading3_7.book_name = "Kotlin-Essentials.pdf"
        Essentials!!.add(heading3_7)

        val heading3_8 = Item()
        heading3_8.page_numer = 78
        heading3_8.title = "Chapter 8"
        heading3_8.book_name = "Kotlin-Essentials.pdf"
        Essentials.add(heading3_8)

        val heading3_9 = Item()
        heading3_9.page_numer = 88
        heading3_9.title = "Chapter 9"
        heading3_9.book_name = "Kotlin-Essentials.pdf"
        Essentials.add(heading3_9)

        val heading3_10 = Item()
        heading3_10.page_numer = 92
        heading3_10.title = "Chapter 10"
        heading3_10.book_name = "Kotlin-Essentials.pdf"
        Essentials.add(heading3_10)

        val heading3_11 = Item()
        heading3_11.page_numer = 96
        heading3_11.title = "Chapter 11"
        heading3_11.book_name = "Kotlin-Essentials.pdf"
        Essentials.add(heading3_11)
        ///=============================================================================
        ///=============================================================================
        ///=============================================================================
        ///=============================================================================
        ///=============================================================================
        val heading4_1 = Item()
        heading4_1.page_numer = 0
        heading4_1.title = "Full Book"
        heading4_1.book_name = "Programming-Kotlin.pdf"
        Programming_Kotlin.add(heading4_1)

        //=======================================================

        listDataChild!![listDataHeader!!.get(0)] = cheatsheet// Header, Child data
        listDataChild!![listDataHeader!!.get(1)] = ful_documantation
        listDataChild!![listDataHeader!!.get(2)] = Essentials
        listDataChild!![listDataHeader!!.get(3)] = Programming_Kotlin
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main_drawer, menu)
        return true }
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

