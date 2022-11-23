package com.example.mybookkeeperassistant

import MyAdapater.AnalysisPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
import androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener
import com.github.mikephil.charting.charts.BarChart
import database.ManageDB
import frag_chart.ExpenseChart
import frag_chart.IncomeChart
import frag_check_chart.ExpenseCheckChart
import frag_check_chart.IncomeCheckChart
import kotlinx.android.synthetic.main.activity_chart_analysis.*
import utils.CalendarDialog
import java.time.Month
import java.time.Year
import java.util.*
import java.util.zip.Inflater
import kotlin.math.min

class ChartAnalysis : AppCompatActivity() {
    lateinit var incomeButton: Button
    lateinit var expenseButton: Button
    lateinit var analysisList:MutableList<Fragment>
    lateinit var analysisPager:ViewPager
    lateinit var incomeAnalysisChart:IncomeCheckChart
    lateinit var expenseAnalysisChart:ExpenseCheckChart

    var kind = 0;
    var year = 0;
    var userId = -1
    var month = 0
    var seletedYear = -1
    var seletedMonth = -1
    lateinit var month_str:String
    lateinit var analysisAdapter:AnalysisPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_analysis)
        initializeViews()
        setCurrentTime()
        analysisFrag()
        setScrollListener()
    }





    private fun setScrollListener() {

        analysisPager.addOnPageChangeListener(object : SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                changedBtnStyle(position)
                changedBgStyle(position)
            }
        })
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


    private fun analysisFrag() {
        analysisList = java.util.ArrayList()
        //add fragment
        incomeAnalysisChart = IncomeCheckChart(userId)
        expenseAnalysisChart = ExpenseCheckChart(userId)
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
        analysisAdapter = AnalysisPagerAdapter(supportFragmentManager,analysisList)
        analysisPager.adapter = analysisAdapter
        //add fragment into chart

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

    private fun initializeViews() {
        userId = intent.getIntExtra("userID",-1)
        incomeButton = chart_income_btn
        expenseButton = chart_expense_btn

        analysisPager = chart_analysis

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
        chart_calendar.setOnClickListener{
            showCalendarDialog()
        }
        chart_back.setOnClickListener{
            finish()
        }
    }

    private fun showCalendarDialog() {
//        Log.d("beforeseYear",selectedYear.toString())
//        Log.d("beforeseMonth",selectedMonth.toString())

        val dialog = CalendarDialog(this,seletedYear,seletedMonth,userId)
        dialog.show()
        dialog.setDialogSize()
        dialog.updateHistory = object : CalendarDialog.UpdateHistory {
            override fun update(sele: Int, year: Int, month: Int) {

                seletedYear = sele
                seletedMonth = month
                incomeAnalysisChart.updateTime(year, month)
                expenseAnalysisChart.updateTime(year, month)

//                Log.d("seYear",selectedYear.toString())
//                Log.d("seMonth",selectedMonth.toString())

            }

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
}