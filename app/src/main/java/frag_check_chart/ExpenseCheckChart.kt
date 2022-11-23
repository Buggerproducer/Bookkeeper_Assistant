package frag_check_chart

import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mybookkeeperassistant.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import database.BarBean
import database.ManageDB
import java.util.*

class ExpenseCheckChart(userID: Int) : BaseCheckChart(userID) {
    lateinit var monthExpenseList:List<BarBean>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        monthExpenseList = ManageDB.getEveryDayOfMonth(year,month,0,userID)
        if (monthExpenseList.size == 0) {
            chartLayout.visibility = View.GONE
            empty.visibility = View.VISIBLE
        }else{
            chartLayout.visibility = View.VISIBLE
            empty.visibility = View.GONE
        }
        return view
    }


    override fun setMBCAxisData(year: Int, month: Int) {
        super.setMBCAxisData(year, month)
        //reference 5
        val mySets:MutableList<IBarDataSet>
        mySets = java.util.ArrayList()
        //get the total of everyday
        val dayList = ManageDB.getEveryDayOfMonth(year,month,0,userID)
        //if empty, gone
        if (dayList.size == 0) {
            chartLayout.visibility = View.GONE
            empty.visibility = View.VISIBLE
        }else{
            chartLayout.visibility = View.VISIBLE
            empty.visibility = View.GONE
        }

        val myEntrys:MutableList<BarEntry> = java.util.ArrayList()
        for (index in 0..30){
            val entry = BarEntry(index.toFloat(),0f)
            myEntrys.add(entry)
        }
        for (index in 0..(dayList.size-1)){
            val bb:BarBean = dayList.get(index)
            val day = bb.getDay()
            // get the pos based on day
            val x = day?.minus(1)
            val bar = x?.let { myEntrys.get(it) }
            // set price
            if (bar != null) {
                bar.y = bb.getTotal()!!
            }

        }

//        for(index in 0..(myEntrys.size-1)){
//            Log.d("y"+index,myEntrys.get(index).y.toString())
//            Log.d("x"+index,myEntrys.get(index).x.toString())
//
//
//        }

        val myBarSet = BarDataSet(myEntrys,"")
        myBarSet.valueTextColor = Color.BLACK
        myBarSet.valueTextSize = 8f
        myBarSet.color = Color.parseColor("#608dd1")
        //reference5
        myBarSet.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            if (value == 0f){
                return@setValueFormatter ""
            }
            return@setValueFormatter value.toString()+""
        }

        mySets.add(myBarSet)

        val data = BarData(mySets)
        data.barWidth = 0.4f
        monthBarChart.data = data

        //reference 6
        // 设置y轴的标签大小

        monthBarChart.isDoubleTapToZoomEnabled = false


    }
    //refernce 5
    override fun setMBCYAxis(year: Int, month: Int) {
        super.setMBCYAxis(year, month)
        val maxExpense = ManageDB.getMaxOneMonth(year,month,0,userID)
        val max = Math.ceil(maxExpense.toDouble())//up integrate

        // 设置标签对x轴的偏移量，垂直方向
        // 设置y轴，y轴有两条，分别为左和右
        val yAxis_right: YAxis = monthBarChart.getAxisRight()
        yAxis_right.setAxisMaximum(max.toFloat())  // 设置y轴的最大值
        yAxis_right.setAxisMinimum(0f)  // 设置y轴的最小值
        yAxis_right.setEnabled(false)  // 不显示右边的y轴

        // 不显示右边的y轴
        val yAxis_left: YAxis = monthBarChart.getAxisLeft()
        yAxis_left.setAxisMaximum(max.toFloat())
        yAxis_left.setAxisMinimum(0f)
        yAxis_left.setTextSize(15f) // 设置y轴的标签大小
        yAxis_left.isEnabled = false

        val legend = monthBarChart.legend
        legend.isEnabled = false

    }

    override fun setMLCAxisData(year: Int, month: Int) {
        super.setMLCAxisData(year, month)
        val mySets:MutableList<ILineDataSet>
        mySets = java.util.ArrayList()
        //get the total of everyday
        val dayList = ManageDB.getEveryDayOfMonth(year,month,0,userID)
        //if empty, gone
        if (dayList.size == 0) {
            chartLayout.visibility = View.GONE
            empty.visibility = View.VISIBLE
        }else{
            chartLayout.visibility = View.VISIBLE
            empty.visibility = View.GONE
        }

        val myEntrys:MutableList<Entry> = java.util.ArrayList()
        for (index in 0..30){
            val entry = Entry(index.toFloat(),0f)
            myEntrys.add(entry)
        }
        for (index in 0..(dayList.size-1)){
            val bb:BarBean = dayList.get(index)
            val day = bb.getDay()
            // get the pos based on day
            val x = day?.minus(1)
            val line = x?.let { myEntrys.get(it) }
            // set price
            if (line != null) {
                line.y = bb.getTotal()!!
            }

        }

//        for(index in 0..(myEntrys.size-1)){
//            Log.d("y"+index,myEntrys.get(index).y.toString())
//            Log.d("x"+index,myEntrys.get(index).x.toString())
//
//
//        }

        val myBarSet = LineDataSet(myEntrys,"")
        myBarSet.valueTextColor = Color.BLACK
        myBarSet.valueTextSize = 8f
        myBarSet.setDrawFilled(true)
        myBarSet.fillDrawable = resources.getDrawable(R.drawable.line_bg_blue)
        

        myBarSet.color = resources.getColor(R.color.orange)
        //reference5
        myBarSet.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            if (value == 0f){
                return@setValueFormatter ""
            }
            return@setValueFormatter value.toString()+""
        }

        mySets.add(myBarSet)

        val data = LineData(mySets)
        monthLineChart.data = data

        //reference 6
        // 设置y轴的标签大小

        monthLineChart.isDoubleTapToZoomEnabled = false

    }

    override fun setMLCYAxis(year: Int, month: Int) {
        super.setMLCYAxis(year, month)
        val maxExpense = ManageDB.getMaxOneMonth(year,month,0,userID)
        val max = Math.ceil(maxExpense.toDouble())//up integrate

        // 设置标签对x轴的偏移量，垂直方向
        // 设置y轴，y轴有两条，分别为左和右
        val yAxis_right: YAxis = monthBarChart.getAxisRight()
        yAxis_right.setAxisMaximum(max.toFloat())  // 设置y轴的最大值
        yAxis_right.setAxisMinimum(0f)  // 设置y轴的最小值
        yAxis_right.setEnabled(false)  // 不显示右边的y轴

        // 不显示右边的y轴
        val yAxis_left: YAxis = monthBarChart.getAxisLeft()
        yAxis_left.setAxisMaximum(max.toFloat())
        yAxis_left.setAxisMinimum(0f)
        yAxis_left.setTextSize(15f) // 设置y轴的标签大小

        val legend = monthBarChart.legend
        legend.isEnabled = false
    }

    override fun setPieData(year: Int, month: Int) {
        super.setPieData(year, month)
        val strings: MutableList<PieEntry> = ArrayList()
        val pie_datalist = ManageDB.getAnalysisListData(year,month,0,userID)
        var ratio=0f
        for (index in 0..(pie_datalist.size-1)){
            if (index < 3){
                val piebean = pie_datalist.get(index)
                ratio = ratio + piebean.percent!!
                piebean.percent?.let { PieEntry(it,piebean.type) }?.let { strings.add(it) }
            }else{
                break
            }
        }
        if (pie_datalist.size>4){
            strings.add(PieEntry(1-ratio,"Others"))
        }

        val dataSet = PieDataSet(strings, "")
        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.orange))
        colors.add(resources.getColor(R.color.blue))
        colors.add(resources.getColor(R.color.orange_dark))
        colors.add(resources.getColor(R.color.blue_dark))
        colors.add(resources.getColor(R.color.dark_grey))
        dataSet.colors = colors
        val pieData = PieData(dataSet)
        pieData.setDrawValues(true)
        typePieChart.setData(pieData)
        typePieChart.invalidate()
    }

//    override fun initTypeBar(year: Int, month: Int) {
//        super.initTypeBar(year, month)
//        val xAxis = typeBarChart.xAxis
//        val list = java.util.ArrayList<BarEntry>()
//        val pie_datalist = ManageDB.getAnalysisListData(year,month,0,userID)
//        var ratio=0f
//        var xAxisValue = java.util.ArrayList<String>()
//        for (index in 0..(pie_datalist.size-1)){
//            if (index <= 3){
//                val piebean = pie_datalist.get(index)
//                ratio = ratio + piebean.sumM!!
//                list.add(BarEntry(index.toFloat(), piebean.sumM!!))
//                piebean.type?.let { xAxisValue.add(it) }
//                val test_list = java.util.ArrayList<BarEntry>()
//                test_list.add(BarEntry(0f, piebean.sumM!!))
//                val barDataSet= BarDataSet(test_list,piebean.type);
//                val barData= BarData(barDataSet);
//                typeBarChart.setData(barData);
//            }else{
//                break
//            }
//        }
//        val rest = ManageDB.getOneMonthTotal(year,month,0,userID) - ratio
//        xAxis.setLabelCount(list.size)
//        if (pie_datalist.size>4){
//            list.add(BarEntry(4f,rest))
//            xAxisValue.add("Others")
//            val test_list = java.util.ArrayList<BarEntry>()
//            test_list.add(BarEntry(0f,rest))
//            val barDataSet= BarDataSet(test_list,"Others");
//            val barData= BarData(barDataSet);
//            typeBarChart.setData(barData);
//
//            xAxis.setLabelCount(4)
//        }
//


//        val barDataSet= BarDataSet(list,"横向柱状图");
//        val barData= BarData(barDataSet);


//
//        typeBarChart.getDescription().setEnabled(false);//隐藏右下角英文
//        typeBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//X轴的位置 默认为右边
//        typeBarChart.getAxisLeft().setEnabled(false);//隐藏上侧Y轴   默认是上下两侧都有Y轴
//

//    }
}