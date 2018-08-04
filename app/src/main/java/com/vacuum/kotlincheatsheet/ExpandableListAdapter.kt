package com.vacuum.kotlincheatsheet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Typeface
import android.support.v4.view.GravityCompat
import android.util.Log
import android.view.View
import android.widget.*
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle


class ExpandableListAdapter(private val mContext: Context, private val pdfView: PDFView, private val mListDataHeader: List<ExpandedMenuModel> // header titles
                            , // child data in format of header title, child title
                            private val mListDataChild: HashMap<ExpandedMenuModel, List<Item>>, internal var expandList: ExpandableListView) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        val i = mListDataHeader.size
        Log.d("GROUPCOUNT", i.toString())
        return this.mListDataHeader.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        var childCount = 0
        /*if (groupPosition != 3) {
            childCount = this.mListDataChild[this.mListDataHeader[groupPosition]]!!.size
        }*/
        return this.mListDataChild[this.mListDataHeader[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.mListDataHeader[groupPosition]
    }
    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        Log.d("CHILD", mListDataChild[this.mListDataHeader[groupPosition]]
                !!.get(childPosition).title)
        return this.mListDataChild[this.mListDataHeader[groupPosition]]!!.get(childPosition).title
    }
    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }
    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val headerTitle = getGroup(groupPosition) as ExpandedMenuModel
        if (convertView == null) {
            val infalInflater = this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.listheader, null)
        }
        val lblListHeader = convertView!!
                .findViewById(R.id.submenu) as TextView
        val headerIcon = convertView!!.findViewById(R.id.iconimage) as ImageView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.setText(headerTitle.iconName)
        headerIcon.setImageResource(headerTitle.iconImg)
        return convertView
    }
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val childText = getChild(groupPosition, childPosition) as String

        if (convertView == null) {
            val infalInflater = this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_submenu, null)
        }
        val txtListChild = convertView!!
                .findViewById(R.id.submenu) as TextView
        txtListChild.text = childText
        txtListChild.setOnClickListener {  view ->
            displayFromAsset(this.mListDataChild[this.mListDataHeader[groupPosition]]!!.get(childPosition).book_name,this.mListDataChild[this.mListDataHeader[groupPosition]]!!.get(childPosition).page_numer)
        }
        return convertView
    }
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    private fun displayFromAsset(path:String = "kotlincheatsheet.pdf" ,page:Int = 5) {
        //kotlincheatsheet.pdf
        //kotlin-full.pdf
        pdfView!!.fromAsset(path)
                .defaultPage(page)
                .enableAnnotationRendering(true)
                .scrollHandle(DefaultScrollHandle(mContext))
                .spacing(10) // in dp
                .load()
    }
}