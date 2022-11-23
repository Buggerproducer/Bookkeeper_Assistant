package frag_chart

import MyAdapater.AnalysisListAdapter
import android.graphics.Color
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.mybookkeeperassistant.R
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import database.AnalysisBean
import database.BarBean
import database.ManageDB
import kotlinx.android.synthetic.main.fragment_base_chart.*
import java.time.Month
import java.time.Year
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExpenseChart.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpenseChart(userID: Int) : BaseChart(userID){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mpChart_1.setBackgroundResource(R.mipmap.background1_orange)
        return view
    }


    override fun onResume() {
        super.onResume()

        showAnalysisList(year,month,0)
    }

    override fun updateTime(year: Int, month: Int) {
        super.updateTime(year, month)
        showAnalysisList(year,month,0)


    }

    override fun setAxisData(year: Int, month: Int) {
        super.setAxisData(year, month)
        //reference 5
        val mySets:MutableList<IBarDataSet>
        mySets = java.util.ArrayList()
        //get the total of everyday
        val dayList = ManageDB.getEveryDayOfMonth(year,month,0,userID)
        //if empty, gone
        if (dayList.size == 0) {
            mpChart_1.visibility = View.GONE
            empty.visibility = View.VISIBLE
        }else{
            mpChart_1.visibility = View.VISIBLE
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
        mpChart_1.data = data

        //reference 6
        // 设置y轴的标签大小
        val m = Matrix()
        m.postScale(3f, 1f)//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
        mpChart_1.getViewPortHandler().refresh(m,mpChart_1,false) //将图表动画显示之前进行缩放


        mpChart_1.animateX(1000) // 立即执行的动画,x轴

        val day = Calendar.getInstance().get(Calendar.DATE)
        mpChart_1.moveViewToX( day.toFloat()-1-10)

        mpChart_1.isDoubleTapToZoomEnabled = false


    }
    //refernce 5
    override fun setYAxis(year: Int, month: Int) {
        super.setYAxis(year, month)
        val maxExpense = ManageDB.getMaxOneMonth(year,month,0,userID)
        val max = Math.ceil(maxExpense.toDouble())//up integrate

        // 设置标签对x轴的偏移量，垂直方向
        // 设置y轴，y轴有两条，分别为左和右
        val yAxis_right: YAxis = mpChart_1.getAxisRight()
        yAxis_right.setAxisMaximum(max.toFloat())  // 设置y轴的最大值
        yAxis_right.setAxisMinimum(0f)  // 设置y轴的最小值
        yAxis_right.setEnabled(false)  // 不显示右边的y轴

        // 不显示右边的y轴
        val yAxis_left: YAxis = mpChart_1.getAxisLeft()
        yAxis_left.setAxisMaximum(max.toFloat())
        yAxis_left.setAxisMinimum(0f)
        yAxis_left.setTextSize(15f) // 设置y轴的标签大小
        yAxis_left.isEnabled = false

        val legend = mpChart_1.legend
        legend.isEnabled = false

    }
}