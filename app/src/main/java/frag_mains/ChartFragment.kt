package frag_mains

import MyAdapater.AnalysisPagerAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.mybookkeeperassistant.R
import com.github.mikephil.charting.charts.BarChart
import database.ManageDB
import frag_chart.ExpenseChart
import frag_chart.IncomeChart
import kotlinx.android.synthetic.main.activity_chart_analysis.*
import utils.CalendarDialog
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChartFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var incomeButton: Button
    lateinit var expenseButton: Button
    lateinit var info_title: TextView
    lateinit var info_income: TextView
    lateinit var info_expense: TextView
    lateinit var analysisList:MutableList<Fragment>
    lateinit var analysisPager: ViewPager
    lateinit var incomeAnalysisChart: IncomeChart
    lateinit var expenseAnalysisChart: ExpenseChart

    var userId =-1
    var kind = 0;
    var year = 0;
    var month = 0
    var seletedYear = -1
    var seletedMonth = -1
    lateinit var month_str:String
    lateinit var analysisAdapter: AnalysisPagerAdapter
    lateinit var t:FragmentActivity
    constructor(t:FragmentActivity,userID:Int):this(){
        this.userId = userID
        this.t = t
    }

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
        val mview = inflater.inflate(R.layout.fragment_chart_analysis, container, false)
        initializeViews(mview)
        setCurrentTime()
        loadAnalysis(year,month)
        analysisFrag()
        setScrollListener()
//        Log.d("oncreateView","oncreatView")
        return mview
    }
    private fun setScrollListener() {

        analysisPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                changedBtnStyle(position)
                changedBgStyle(position)
            }
        })
    }

    private fun analysisFrag() {
        analysisList = java.util.ArrayList()
        //add fragment
        incomeAnalysisChart = IncomeChart(userId)
        expenseAnalysisChart = ExpenseChart(userId)
        //add data to fragments
        val b = Bundle()
        b.putInt("year",year)
        b.putInt("month",month)
        incomeAnalysisChart.arguments = b
        expenseAnalysisChart.arguments = b
        //add fragment to data source
        analysisList.add(expenseAnalysisChart)
        analysisList.add(incomeAnalysisChart)
        //set adapter
        analysisAdapter = AnalysisPagerAdapter(childFragmentManager,analysisList)
        analysisPager.adapter = analysisAdapter
        //add fragment into chart

        Log.d("fragment1",incomeAnalysisChart.toString())
        Log.d("fragment2",expenseAnalysisChart.toString())
        Log.d("anaylist",analysisList.get(0).toString())


    }


    private fun setCurrentTime() {
        year = Calendar.getInstance().get(Calendar.YEAR)
        month = Calendar.getInstance().get(Calendar.MONTH)+1
        when(month){
            1 -> month_str = "Jan"
            2 -> month_str = "Feb"
            3 -> month_str = "Mar"
            4 -> month_str = "Apr"
            5 -> month_str = "May"
            6 -> month_str = "Jun"
            7 -> month_str = "Jul"
            8 -> month_str = "Aug"
            9 -> month_str = "Sept"
            10 -> month_str = "Oct"
            11 -> month_str = "Nov"
            12 -> month_str = "Dec"
        }

    }

    private fun initializeViews(mview: View) {
        incomeButton = mview.findViewById(R.id.chart_income_btn)
        expenseButton = mview.findViewById(R.id.chart_expense_btn)
        info_title = mview.findViewById(R.id.chart_date)
        info_income = mview.findViewById(R.id.chart_income)
        info_expense = mview.findViewById(R.id.chart_expense)
        analysisPager = mview.findViewById(R.id.chart_analysis)

        incomeButton.setOnClickListener {
            kind = 1
            changedBtnStyle(kind)
            changedBgStyle(kind)
            analysisPager.setCurrentItem(1)
        }
        expenseButton.setOnClickListener {
            kind = 0
            changedBgStyle(kind)
            changedBtnStyle(kind)
            analysisPager.setCurrentItem(0)
        }
        val calendarimg:ImageView = mview.findViewById(R.id.chart_calendar)
        calendarimg.setOnClickListener{
            showCalendarDialog()
        }
        val backImg:ImageView = mview.findViewById(R.id.chart_back)
        backImg.setOnClickListener{
            t.finish()
        }
    }
    private fun changedBgStyle(position: Int) {
        val view = layoutInflater.inflate(R.layout.frag_chart_header,null)
        val chart = view.findViewById<BarChart>(R.id.chart_header_chart)
        if (position == 1){
            chart.setBackgroundResource(R.mipmap.background1_orange)
        }else if (position == 0){
            chart.setBackgroundResource(R.mipmap.background1)
        }
    }
    private fun changedBtnStyle(kind: Int) {
        if (kind == 1){
            incomeButton.setBackgroundResource(R.drawable.chart_btn_activate)
            incomeButton.setTextColor( getResources().getColor(R.color.orange))
            expenseButton.setBackgroundResource(R.drawable.chart_btn)
            expenseButton.setTextColor( getResources().getColor(R.color.black))

        }else if (kind == 0){
            expenseButton.setBackgroundResource(R.drawable.chart_btn_activate)
            expenseButton.setTextColor( getResources().getColor(R.color.orange))
            incomeButton.setBackgroundResource(R.drawable.chart_btn)
            incomeButton.setTextColor( getResources().getColor(R.color.black))

        }
    }

    private fun showCalendarDialog() {
//        Log.d("beforeseYear",selectedYear.toString())
//        Log.d("beforeseMonth",selectedMonth.toString())

        val dialog = CalendarDialog(t,seletedYear,seletedMonth,userId)
        dialog.show()
        dialog.setDialogSize()
        dialog.updateHistory = object : CalendarDialog.UpdateHistory {
            override fun update(sele: Int, year: Int, month: Int) {

                seletedYear = sele
                seletedMonth = month
                loadAnalysis(year,month)
                incomeAnalysisChart.updateTime(year, month)
                expenseAnalysisChart.updateTime(year, month)

//                Log.d("seYear",selectedYear.toString())
//                Log.d("seMonth",selectedMonth.toString())

            }

        }


    }

    private fun loadAnalysis(year:Int,month: Int) {
        val incomeTotal = ManageDB.getOneMonthTotal(year,month,1,userId)
        val incomeNum = ManageDB.getOneMonthTotalNum(year, month, 1,userId)
        val expenseTotal = ManageDB.getOneMonthTotal(year, month, 0,userId)
        val expenseNum = ManageDB.getOneMonthTotalNum(year, month, 0,userId)
        when(month){
            1 -> month_str = "Jan"
            2 -> month_str = "Feb"
            3 -> month_str = "Mar"
            4 -> month_str = "Apr"
            5 -> month_str = "May"
            6 -> month_str = "Jun"
            7 -> month_str = "Jul"
            8 -> month_str = "Aug"
            9 -> month_str = "Sept"
            10 -> month_str = "Oct"
            11 -> month_str = "Nov"
            12 -> month_str = "Dec"
        }
        info_title.setText("Billing Statistics for $month_str $year")
        info_expense.setText("A total of $expenseNum expenditures, total: ¥$expenseTotal")
        info_income.setText("A total of $incomeNum incomes, total: ¥$incomeTotal")

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChartFragment.
         */
        // TODO: Rename and change types and number of parameters

    }
}