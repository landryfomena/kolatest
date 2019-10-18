package com.example.kolatest.adapters

import android.content.Context
import android.graphics.Typeface

import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import com.example.kolatest.model.DataModel
import android.view.ViewGroup
import android.view.animation.AnimationUtils

import com.google.android.material.snackbar.Snackbar
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.kolatest.R


class CustomAdapter(private val dataSet: ArrayList<DataModel>, internal var mContext: Context) :
    ArrayAdapter<DataModel>(mContext, R.layout.row_item, dataSet), View.OnClickListener {

    private var lastPosition = -1

    // View lookup cache
    private class ViewHolder {
        internal var txtName: TextView? = null
        internal var txtType: TextView? = null
        internal var txtVersion: TextView? = null
        internal var info: ImageView? = null
    }

    override  fun onClick(v: View) {

        val position = v.getTag() as Int
        val `object` = getItem(position)

        when (v.getId()) {
            R.id.item_info -> Snackbar.make(
                v,
                "Release date " + `object`!!.feature,
                Snackbar.LENGTH_LONG
            )
                .setAction("No action", null).show()
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val dataModel = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag

        val result: View

        if (convertView == null) {

            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)

            convertView = inflater.inflate(R.layout.row_item, parent, false)
            viewHolder.txtName = convertView!!.findViewById(R.id.name)

            viewHolder.txtType = convertView!!.findViewById(R.id.type)

            viewHolder.txtVersion = convertView!!.findViewById(R.id.version_number)
            
            viewHolder.info = convertView!!.findViewById(R.id.item_info) as ImageView


            result = convertView

            convertView!!.setTag(viewHolder)
        } else {
            viewHolder = convertView!!.getTag() as ViewHolder
            result = convertView
        }

        val animation = AnimationUtils.loadAnimation(
            mContext,
            if (position > lastPosition) R.anim.fui_slide_out_left else R.anim.fui_slide_out_left
        )
        result.startAnimation(animation)
        lastPosition = position

        viewHolder.txtName!!.text = dataModel!!.name
        viewHolder.txtType!!.text = dataModel.type
        viewHolder.txtVersion!!.text = dataModel.version_number
        viewHolder.info!!.setOnClickListener(this)
        viewHolder.info!!.setTag(position)
        // Return the completed view to render on screen
        return convertView
    }
}