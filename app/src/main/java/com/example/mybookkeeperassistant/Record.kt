package com.example.mybookkeeperassistant

import MyAdapater.RecordAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_record.*
import record_frag.ExpenseRecord
import record_frag.IncomeRecord

class Record : AppCompatActivity() {
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    var userID = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        userID = intent.getIntExtra("user_id",-1)
        record_back.setOnClickListener{
            finish()
        }
        //find controller

        tabLayout = record_tabs
        viewPager = record_vp
        //set the loading page of the viewpager
        initializedPager();
    }

    private fun initializedPager() {
        //initialize the set of the viewpager
        var list:MutableList<Fragment> = ArrayList()
        //construct the expense and income page, put into the fragment
        val expenseFragment = ExpenseRecord(this,userID)//expense
        val incomeFragment = IncomeRecord(this,userID)//income
        list.add(expenseFragment)
        list.add(incomeFragment)

        //create the com.example.mybookkeeperassistant.adapter
        val adapter = RecordAdapter(supportFragmentManager,list)

        viewPager?.adapter = adapter

        tabLayout?.setupWithViewPager(viewPager)




    }
}