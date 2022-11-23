package frag_chart

import MyAdapater.AnalysisListAdapter
import android.content.Intent
import android.opengl.Matrix
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import com.example.mybookkeeperassistant.R
import com.example.mybookkeeperassistant.TotalDetail
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import database.AnalysisBean
import database.ManageDB
import java.util.zip.Inflater

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseChart.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseChart(val userID:Int) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var analysisList: ListView
    lateinit var mpChart_1:BarChart
    lateinit var empty:TextView
    var year = 0
    var month = 0
    lateinit var dataList:MutableList<AnalysisBean>
    lateinit var analysisListAdapter: AnalysisListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_base_chart,container,false)
        analysisList = myView.findViewById(R.id.chart_frag_list)
        //get the data passed by activity
        val b = arguments
        year = b?.getInt("year")!!
        month = b?.getInt("month")!!
        //set data source
        dataList = java.util.ArrayList()
        //set adapter
        analysisListAdapter = context?.let { AnalysisListAdapter(it,dataList) }!!
        analysisList.adapter = analysisListAdapter
        //add header
        addHeader()
        Log.d("baseChart","create")

        return myView
    }


    private fun setListListener() {

        analysisList.setOnItemClickListener() { adapterView, view, i, l ->
            val intent = Intent(context,TotalDetail::class.java)
            intent.putExtra("user_id",userID)
            intent.putExtra("type",dataList.get(i-1).type)
            intent.putExtra("sImageId",dataList.get(i-1).selectedImageID)
            intent.putExtra("year",year)
            intent.putExtra("month",month)
            startActivity(intent)
        }
    }

    private fun addHeader() {
        //convert layout into the view
        val headerView = layoutInflater.inflate(R.layout.frag_chart_header,null)
        //add header to the list
        analysisList.addHeaderView(headerView)
        //the controller in the hv
        mpChart_1 = headerView.findViewById(R.id.chart_header_chart)
        empty = headerView.findViewById(R.id.chart_header_nodata)
        //begin reference 5
        //no description
        mpChart_1.description.isEnabled = false
        //distance among bars
        mpChart_1.setExtraOffsets(20F,20F,20F,20F)
        setAxis(year,month)
        setAxisData(year,month)


    }
    //children
    open fun setAxisData(year: Int, month: Int) {

    }

    open fun setYAxis(year: Int,month: Int){

    }


    //reference 5
    fun setAxis(year: Int, month: Int) {
        val xAxis: XAxis = mpChart_1.getXAxis()
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
                    1f-> return@setValueFormatter "2"
                    2f-> return@setValueFormatter "3"
                    3f-> return@setValueFormatter "4"
                    4f-> return@setValueFormatter "5"
                    5f-> return@setValueFormatter "6"
                    6f-> return@setValueFormatter "7"
                    7f-> return@setValueFormatter "8"
                    8f-> return@setValueFormatter "9"
                    9f-> return@setValueFormatter "10"
                    10f-> return@setValueFormatter "11"
                    11f-> return@setValueFormatter "12"
                    12f-> return@setValueFormatter "13"
                    13f-> return@setValueFormatter "14"
                    14f-> return@setValueFormatter "15"
                    15f-> return@setValueFormatter "16"
                    16f-> return@setValueFormatter "17"
                    17f-> return@setValueFormatter "18"
                    18f-> return@setValueFormatter "19"
                    19f-> return@setValueFormatter "20"
                    20f-> return@setValueFormatter "21"
                    21f-> return@setValueFormatter "22"
                    22f-> return@setValueFormatter "23"
                    23f-> return@setValueFormatter "24"
                    24f-> return@setValueFormatter "25"
                    25f-> return@setValueFormatter "26"
                    26f-> return@setValueFormatter "27"
                    27f-> return@setValueFormatter "28"

                }
            } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                when(v){
                    0f-> return@setValueFormatter "1"
                    1f-> return@setValueFormatter "2"
                    2f-> return@setValueFormatter "3"
                    3f-> return@setValueFormatter "4"
                    4f-> return@setValueFormatter "5"
                    5f-> return@setValueFormatter "6"
                    6f-> return@setValueFormatter "7"
                    7f-> return@setValueFormatter "8"
                    8f-> return@setValueFormatter "9"
                    9f-> return@setValueFormatter "10"
                    10f-> return@setValueFormatter "11"
                    11f-> return@setValueFormatter "12"
                    12f-> return@setValueFormatter "13"
                    13f-> return@setValueFormatter "14"
                    14f-> return@setValueFormatter "15"
                    15f-> return@setValueFormatter "16"
                    16f-> return@setValueFormatter "17"
                    17f-> return@setValueFormatter "18"
                    18f-> return@setValueFormatter "19"
                    19f-> return@setValueFormatter "20"
                    20f-> return@setValueFormatter "21"
                    21f-> return@setValueFormatter "22"
                    22f-> return@setValueFormatter "23"
                    23f-> return@setValueFormatter "24"
                    24f-> return@setValueFormatter "25"
                    25f-> return@setValueFormatter "26"
                    26f-> return@setValueFormatter "27"
                    27f-> return@setValueFormatter "28"
                    28f-> return@setValueFormatter "29"
                    29f-> return@setValueFormatter "30"



                }
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                when(v){
                    0f-> return@setValueFormatter "1"
                    1f-> return@setValueFormatter "2"
                    2f-> return@setValueFormatter "3"
                    3f-> return@setValueFormatter "4"
                    4f-> return@setValueFormatter "5"
                    5f-> return@setValueFormatter "6"
                    6f-> return@setValueFormatter "7"
                    7f-> return@setValueFormatter "8"
                    8f-> return@setValueFormatter "9"
                    9f-> return@setValueFormatter "10"
                    10f-> return@setValueFormatter "11"
                    11f-> return@setValueFormatter "12"
                    12f-> return@setValueFormatter "13"
                    13f-> return@setValueFormatter "14"
                    14f-> return@setValueFormatter "15"
                    15f-> return@setValueFormatter "16"
                    16f-> return@setValueFormatter "17"
                    17f-> return@setValueFormatter "18"
                    18f-> return@setValueFormatter "19"
                    19f-> return@setValueFormatter "20"
                    20f-> return@setValueFormatter "21"
                    21f-> return@setValueFormatter "22"
                    22f-> return@setValueFormatter "23"
                    23f-> return@setValueFormatter "24"
                    24f-> return@setValueFormatter "25"
                    25f-> return@setValueFormatter "26"
                    26f-> return@setValueFormatter "27"
                    27f-> return@setValueFormatter "28"
                    28f-> return@setValueFormatter "29"
                    29f-> return@setValueFormatter "30"
                    30f-> return@setValueFormatter "31"
                }

            }
            return@setValueFormatter ""
        }
        xAxis.setYOffset(10f) // 设置标签对x轴的偏移量，垂直方向
        setYAxis(year,month)



    }

    override fun onResume() {
        super.onResume()
        updateTime(year,month)
        showAnalysisList(year,month,0)
        setListListener()
    }

    open fun updateTime(year: Int,month: Int){
        this.year = year
        this.month = month
        mpChart_1.clear()
        mpChart_1.invalidate()
        setAxis(year, month)
        setAxisData(year, month)
        mpChart_1.notifyDataSetChanged()






    }

    open fun showAnalysisList(year: Int,month: Int,kind:Int) {
        val list = ManageDB.getAnalysisListData(year,month, kind,userID)
        dataList.clear()
        dataList.addAll(list)
        analysisListAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExpenseChart.
         */
        // TODO: Rename and change types and number of parameters

    }
}