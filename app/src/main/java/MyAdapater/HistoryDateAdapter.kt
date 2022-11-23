package MyAdapater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mybookkeeperassistant.R

class HistoryDateAdapter(var context: Context,var year:Int):BaseAdapter() {
    var dataList:MutableList<String> = java.util.ArrayList()
    var seleted = -1

    init {
        for (index in 1..12){
            val date = year.toString()+"-"+index.toString()
            dataList.add(date)
        }
    }
    fun updateYear(year:Int){
        this.year = year
        dataList.clear()
        for (index in 1..12){
            val date = year.toString()+"-"+index.toString()
            dataList.add(date)
        }
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(p0: Int): Any {
        return dataList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var v = p1
        v = LayoutInflater.from(context).inflate(R.layout.item_history_month,p2,false)
        val month_textView = v.findViewById<TextView>(R.id.history_id_month)
        month_textView.setText(dataList.get(p0))
        month_textView.setBackgroundResource(R.color.grey)
        month_textView.setTextColor( context.getResources().getColor(R.color.black))
        if (p0 == seleted){
            month_textView.setText(dataList.get(p0))
            month_textView.setBackgroundResource(R.color.blue)
            month_textView.setTextColor( context.getResources().getColor(R.color.white))

        }
        Log.d("p0",p0.toString())
        Log.d("selected",seleted.toString())

        return v


    }
}