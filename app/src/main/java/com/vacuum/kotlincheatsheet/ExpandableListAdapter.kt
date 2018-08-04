package com.vacuum.kotlincheatsheet

import android.content.Context
import android.widget.TextView
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.ViewGroup
import android.graphics.Typeface
import android.util.Log
import android.view.View
import java.nio.file.Files.size
import android.widget.ExpandableListView
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView


class ExpandableListAdapter(private val mContext: Context, private val mListDataHeader: List<ExpandedMenuModel> // header titles
                            , // child data in format of header title, child title
                            private val mListDataChild: HashMap<ExpandedMenuModel, List<String>>, internal var expandList: ExpandableListView) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        val i = mListDataHeader.size
        Log.d("GROUPCOUNT", i.toString())
        return this.mListDataHeader.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        var childCount = 0
        if (groupPosition != 2) {
            childCount = this.mListDataChild[this.mListDataHeader[groupPosition]]!!.size
        }
        return childCount
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.mListDataHeader[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        Log.d("CHILD", mListDataChild[this.mListDataHeader[groupPosition]]
                !!.get(childPosition))
        return this.mListDataChild[this.mListDataHeader[groupPosition]]!!.get(childPosition)
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

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}