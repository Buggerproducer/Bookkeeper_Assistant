package frag_check_chart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mybookkeeperassistant.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import kotlinx.android.synthetic.main.fragment_base_check_chart.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseCheckChart.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseCheckChart (val userID:Int): Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var monthBarChart: BarChart
    lateinit var monthLineChart: LineChart
    lateinit var typePieChart: PieChart
    lateinit var empty:TextView
    lateinit var chartLayout:RelativeLayout
    var year = -1
    var month = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val myview = inflater.inflate(R.layout.fragment_base_check_chart, container, false)
        initView(myview)
        initMonthBarChart()
        initLineChart()
        initPieChart()
        initTypeBar(year, month)
        return myview
    }

    open fun initTypeBar(year: Int, month: Int) {

    }

    private fun initPieChart() {
        val description: Description = Description()
        description.text = ""
        typePieChart.description = description
        typePieChart.setEntryLabelColor(R.color.dark_grey)
        typePieChart.setEntryLabelTextSize(10f)
        typePieChart.isDrawHoleEnabled = false
        setPieData(year,month)
    }



    private fun initLineChart() {
        monthLineChart.description.isEnabled = false
        monthLineChart.setDrawGridBackground(false)

        setMLCAxis(year,month)
        setMLCAxisData(year,month)
    }

    private fun initMonthBarChart() {
        monthBarChart.description.isEnabled = false
        //distance among bars
        monthBarChart.setExtraOffsets(20F,20F,20F,20F)
        setMBCAxis(year,month)
        setMBCAxisData(year,month)
    }



    private fun setMLCAxis(year: Int, month: Int) {
        monthLineChart.animateY(1000)

        val xAxis: XAxis = monthLineChart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)  // 设置x轴显示在下方，默认在上方
        xAxis.setDrawGridLines(false) // 将此设置为true，绘制该轴的网格线。
        xAxis.setLabelCount(31)  // 设置x轴上的标签个数
        xAxis.setTextSize(10f) // x轴上标签的大小
        // 设置x轴显示的值的格式

        xAxis.setValueFormatter { value, axis ->
            val v = value

            if (month == 2) {
                when(v){
                    0f-> return@setValueFormatter "1"

                    14f-> return@setValueFormatter "15"

                    27f-> return@setValueFormatter "28"

                }
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                when(v){
                    0f-> return@setValueFormatter "1"

                    14f-> return@setValueFormatter "15"

                    29f-> return@setValueFormatter "30"



                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                when(v){
                    0f-> return@setValueFormatter "1"

                    14f-> return@setValueFormatter "15"

                    30f-> return@setValueFormatter "31"
                }

            }
            return@setValueFormatter ""
        }
        xAxis.setYOffset(10f) // 设置标签对x轴的偏移量，垂直方向
        setMLCYAxis(year,month)
    }

    open fun setMLCYAxis(year: Int, month: Int) {

    }
    open fun setMLCAxisData(year: Int,month: Int){

    }


    open fun setMBCAxisData(year: Int, month: Int) {
    }



    private fun setMBCAxis(year: Int, month: Int) {
        monthBarChart.animateY(1000)

        val xAxis: XAxis = monthBarChart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)  // 设置x轴显示在下方，默认在上方
        xAxis.setDrawGridLines(false) // 将此设置为true，绘制该轴的网格线。
        xAxis.setLabelCount(31)  // 设置x轴上的标签个数
        xAxis.setTextSize(10f) // x轴上标签的大小
        // 设置x轴显示的值的格式

        xAxis.setValueFormatter { value, axis ->
            val v = value

            if (month == 2) {
                when(v){
                    0f-> return@setValueFormatter "1"

                    14f-> return@setValueFormatter "15"

                    27f-> return@setValueFormatter "28"

                }
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                when(v){
                    0f-> return@setValueFormatter "1"

                    14f-> return@setValueFormatter "15"

                    29f-> return@setValueFormatter "30"



                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                when(v){
                    0f-> return@setValueFormatter "1"

                    14f-> return@setValueFormatter "15"

                    30f-> return@setValueFormatter "31"
                }

            }
            return@setValueFormatter ""
        }
        xAxis.setYOffset(10f) // 设置标签对x轴的偏移量，垂直方向
        setMBCYAxis(year,month)


    }

    open fun setMBCYAxis(year: Int, month: Int) {

    }

    private fun initView(myview: View) {
        monthBarChart = myview.findViewById(R.id.check_chart_bar)
        monthLineChart = myview.findViewById(R.id.check_chart_line)
        typePieChart = myview.findViewById(R.id.check_chart_pie)
        empty = myview.findViewById(R.id.check_chart_header_nodata)
        chartLayout = myview.findViewById(R.id.check_charts)
        val b = arguments
        year = b?.getInt("year")!!
        month = b?.getInt("month")!!
    }
    override fun onResume() {
        super.onResume()
        updateTime(year,month)
    }


    open fun updateTime(year: Int,month: Int) {
        this.year = year
        this.month = month
        monthBarChart.clear()
        monthBarChart.invalidate()
        setMBCAxis(year, month)
        setMBCAxisData(year, month)
        monthBarChart.notifyDataSetChanged()
        monthLineChart.clear()
        monthLineChart.invalidate()
        setMLCAxis(year, month)
        setMLCAxisData(year, month)
        monthLineChart.notifyDataSetChanged()
        typePieChart.clear()
        typePieChart.invalidate()
        setPieData(year, month)

        monthBarChart.notifyDataSetChanged()

//        typeBarChart.clear()
//        typeBarChart.invalidate()
//        initTypeBar(year, month)
//
//        typeBarChart.notifyDataSetChanged()
    }

    open fun setPieData(year: Int, month: Int) {
        typePieChart.animateY(1000)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BaseCheckChart.
         */

    }
}