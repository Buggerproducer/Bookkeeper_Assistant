package com.example.mybookkeeperassistant

import MyAdapater.BillsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import database.BillBean
import database.ManageDB
import kotlinx.android.synthetic.main.activity_total_detail.*
import kotlinx.android.synthetic.main.chart_total_detail.*
import kotlinx.android.synthetic.main.chart_total_detail.total_back
import kotlinx.android.synthetic.main.chart_total_detail.total_text
import java.util.*

class TotalDetail : AppCompatActivity() {
    lateinit var adapter:BillsAdapter
    lateinit var title:TextView
    lateinit var titleImage:ImageView
    lateinit var dataList: MutableList<BillBean?>
    lateinit var totalList:ListView
    lateinit var back:ImageView
    var userID=-1
    var year = 0
    var month=0
    var sImageId = 0
    lateinit var type:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_detail)
        initView()


        adapter = BillsAdapter(this,dataList);
        totalList.adapter = adapter
    }

    private fun initView() {
        title = total_text
        back = total_back
        dataList = java.util.ArrayList()
        titleImage = total_text_image
        totalList = total_list
        userID = intent.getIntExtra("user_id",-1)
        year = intent.getIntExtra("year",Calendar.YEAR)
        month = intent.getIntExtra("month",Calendar.MONTH)
        type = intent.getStringExtra("type").toString()
        sImageId = intent.getIntExtra("sImageId",0)
        titleImage.setImageResource(sImageId)
        title.setText(type)
        back.setOnClickListener{
            finish()
        }
        loadData()



    }

    private fun loadData() {
        val list = ManageDB.getTotalDetail(type,year,month,userID)
        dataList.addAll(list)
    }
}