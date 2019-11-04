package com.example.crudkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.ArrayList

/**
 * Created by Parsania Hardik on 26-Apr-17.
 */
class CustomAdapter(private val context: Context, private val userModelArrayList: ArrayList<UserModel>) :
    BaseAdapter() {


    override fun getCount(): Int {
        return userModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return userModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.activity_lv_item, null, true)

            holder.tvname = convertView!!.findViewById(R.id.name) as TextView
            holder.tvhobby = convertView.findViewById(R.id.hobby) as TextView
            holder.tvcity = convertView.findViewById(R.id.city) as TextView


            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.tvname!!.text = "Name: " + userModelArrayList[position].getNames()
        holder.tvhobby!!.text = "Hobby: " + userModelArrayList[position].getHobbys()
        holder.tvcity!!.text = "City: " + userModelArrayList[position].getCitys()

        return convertView
    }

    private inner class ViewHolder {

        var tvname: TextView? = null
        var tvhobby: TextView? = null
        var tvcity: TextView? = null
    }

}