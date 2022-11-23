package com.example.mybookkeeperassistant

import MyAdapater.MonthBillAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import database.ManageDB
import database.MonthBillBean
import kotlinx.android.synthetic.main.activity_month_bill.*
import java.util.*

class MonthBill : AppCompatActivity() {
    var userID = -1
    var year = -1
    lateinit var monthBillList:ListView
    lateinit var dataList: MutableList<MonthBillBean>
    lateinit var adapter:MonthBillAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month_bill)
        initView()
        adapter = MonthBillAdapter(this,dataList);
        monthBillList.adapter = adapter
    }

    private fun initView() {
        userID = intent.getIntExtra("userID",-1)
        year = intent.getIntExtra("year", Calendar.YEAR)
        dataList = java.util.ArrayList()

        val list = ManageDB.getMonthBill(userID,year)
        dataList.addAll(list)
        month_bill_back.setOnClickListener {
            finish()
        }
        monthBillList = month_bill_list
    }
}