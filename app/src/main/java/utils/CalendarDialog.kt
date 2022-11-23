package utils

import MyAdapater.HistoryDateAdapter
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import com.example.mybookkeeperassistant.R
import database.ManageDB
import kotlinx.android.synthetic.main.history_time.*
import kotlinx.android.synthetic.main.item_history_year.*
import java.util.*

class CalendarDialog(context: Context,year: Int,month: Int,userID:Int):Dialog(context) {

    lateinit var monthGrid:GridView
    lateinit var yearList:LinearLayout
    lateinit var back:ImageView
    lateinit var allYears:MutableList<Int>
    lateinit var allYearsViews:MutableList<TextView>
    var userid = userID
    var selected = year
    var selectedMonth = month
    lateinit var adapter:HistoryDateAdapter
    interface UpdateHistory{
        fun update(sele: Int,year:Int,month:Int)
    }
    var updateHistory: CalendarDialog.UpdateHistory? = null
    @JvmName("setOnEnsureListener1")
    fun setOnUpdateHistory(updateHistory: UpdateHistory) {
        this.updateHistory = updateHistory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_time)
        back = history_error
        monthGrid = history_time_date
        yearList = history_time_year
        back.setOnClickListener{
            cancel()
        }
        addYear()
        initGrid()
    }

    private fun initGrid() {
        val selectedY = allYears.get(selected)
        adapter = HistoryDateAdapter(context,selectedY)
        if (selectedMonth == -1){
            val month = Calendar.getInstance().get(Calendar.MONTH)
            adapter.seleted = month
        }else{
            adapter.seleted = selectedMonth-1
//            Log.d("selected1",selected.toString())
        }
        monthGrid.adapter = adapter
        setMonthListener()

    }

    //add year to the linearlayout
    private fun addYear() {
        allYearsViews = java.util.ArrayList()
        allYears = ManageDB.getAllYears(userid) as MutableList<Int>
        //if there is no record of this year
        if (allYears.size == 0){
            val thisyear = Calendar.getInstance().get(Calendar.YEAR)
            allYears.add(thisyear)
        }
        //traverse add the year to the linearlayout
        for (index in 0..(allYears.size-1)){
//            Log.d("size"+index,allYearsViews.size.toString())
            val y = allYears.get(index)
            val view = layoutInflater.inflate(R.layout.item_history_year,null)
            yearList.addView(view)

            val year_num = view.findViewById<TextView>(R.id.history_year_item)
            year_num.text = y.toString()

            allYearsViews.add(year_num)
//            if (allYearsViews.size>1){
//                Log.d("AddYear"+index, allYearsViews.get(index).text.toString())
//
//                Log.d("before"+index,allYearsViews.get(index-1).text.toString())
//            }

        }

//        print(allYearsViews)

        for (index in 0..(allYears.size -1)){
            val y = allYearsViews.get(index)
//            Log.d("Year"+index, y.toString())

        }

        if (selected == -1){
            selected = allYearsViews.size-1
        }

        changeSelected(selected)
        setYearLinstener()


    }
    private fun setMonthListener(){
        monthGrid!!.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                adapter.seleted = i
                adapter.notifyDataSetChanged()
                val m = i+1
                val y = adapter.year
//                Log.d("m",m.toString())
//                Log.d("y",y.toString())


                updateHistory?.update(selected,y,m)
                cancel()
            }

    }

    private fun setYearLinstener() {
        for (index in 0..(allYearsViews.size-1)){
            val view = allYearsViews.get(index)
//            Log.d("selectedView",view.text.toString())
            val pos = index
            view.setOnClickListener {
                changeSelected(pos)
                selected = pos
                //get the selected year
                val selectedYear = allYears.get(selected)
                adapter.updateYear(selectedYear)

            }
        }
    }


    private fun changeSelected(sele:Int) {
        for (index in 0..(allYears.size-1)){
            val t = allYearsViews.get(index)
            t.setBackgroundResource(R.drawable.item_year_bg)
            t.setTextColor(Color.BLACK)
        }
        val select_view = allYearsViews.get(sele)
        select_view.setBackgroundResource(R.drawable.item_year_activate)
        select_view.setTextColor(Color.WHITE)
    }

    fun setDialogSize() {
        //get window object
        val window = window
        //get the param of the object
        val wlp = window!!.attributes
        //get the width of the screen
        val d = window.windowManager.defaultDisplay
        wlp.width = d.width //dialog width = screen width
        wlp.gravity = Gravity.TOP
        window.setBackgroundDrawableResource(android.R.color.white)
        window.attributes = wlp
    }


}