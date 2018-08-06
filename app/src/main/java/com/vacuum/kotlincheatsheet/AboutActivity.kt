package com.vacuum.kotlincheatsheet

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.about_activity.*

class AboutActivity : AppCompatActivity() {

    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)

        mContext = this

        setup()
    }


    private fun setup() {
        layout_facebook.setOnClickListener {
            val uri = Uri.parse("https://www.facebook.com/Plex.media.company")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        layout_twitter.setOnClickListener{
            val uri = Uri.parse("https://twitter.com/mohamedhima96")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        layout_telegram.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/joinchat/FyEAvQw6fjkaATfUmPWo-A"))
            startActivity(intent)
        }
        layout_contactus.setOnClickListener{

            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "ebrahimm131@gmail.com", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Query")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "hi,...")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }
        layout_honor.setOnClickListener {
            val uri = Uri.parse("https://www.facebook.com/mohamedebrahim93")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        var pInfo: PackageInfo? = null
        var versionName: String? = null
        var versionCode = 0
        try {
            pInfo = mContext!!.packageManager.getPackageInfo(mContext!!.packageName, 0)
            versionName = pInfo!!.versionName
            versionCode = pInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        version_1.text = "$versionName(Code 2854-$versionCode) armV7"

    }

}