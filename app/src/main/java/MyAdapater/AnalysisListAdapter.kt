package MyAdapater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.mybookkeeperassistant.R
import database.AnalysisBean

class AnalysisListAdapter(val context:Context,val dataList:List<AnalysisBean>): BaseAdapter() {
    val inflater = LayoutInflater.from(context)
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(p0: Int): Any {
        return dataList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        var myHolder:ViewHolder
        var myView = p1
        if (p1 == null){
            myView = inflater.inflate(R.layout.chart_item,p2,false)
            myHolder = ViewHolder(myView)
            myView.setTag(myHolder)

        }else{
            myHolder = myView?.getTag() as ViewHolder;
        }
        //get show
        val ab = dataList.get(p0)
        ab.selectedImageID?.let { myHolder.typeImage.setImageResource(it) }
        myHolder.typeName.setText(ab.type)
        val ratio = ab.percent
        val ratio_1 = ratio?.times(100f)
        Log.d("ratio",ratio_1.toString())
        myHolder.percent.setText(ratio_1.toString()+"%")
        myHolder.sum.setText("$ "+ab.sumM.toString())
        return myView

    }

    internal inner class ViewHolder(view: View?){
        var typeImage:ImageView
        var typeName:TextView
        var percent:TextView
        var sum:TextView
        init {
            typeImage = view?.findViewById(R.id.chart_item_typeImage)!!
            typeName = view.findViewById(R.id.chart_item_typeName)
            percent = view.findViewById(R.id.chart_item_typePercent)
            sum = view.findViewById(R.id.chart_item_typeTotal)

        }

    }

}