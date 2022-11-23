
package com.example.mybookkeeperassistant

import MyAdapater.BillsAdapter
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import database.BillBean
import database.ManageDB
import kotlinx.android.synthetic.main.activity_search.*

class Search : AppCompatActivity() {
    lateinit var searchInfo: EditText
    lateinit var resultList: ListView
    lateinit var emptySitua:TextView
    lateinit var dataList:MutableList<BillBean?>
    lateinit var adapter: BillsAdapter
    var userId=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        userId = intent.getIntExtra("user_id",-1)
        initialize()
        dataList = java.util.ArrayList()
        adapter = BillsAdapter(this,dataList)
        resultList.adapter = adapter
        resultList.emptyView = emptySitua
        val levireBrushed = Typeface.createFromAsset(assets,"LeviReBrushed.ttf")
        emptySitua.setTypeface(levireBrushed)

    }

    private fun initialize() {
        searchInfo = search_info
        emptySitua = data_empty
        resultList = search_list
        search_back.setOnClickListener{
            finish()
        }
        search_btn.setOnClickListener{
            val info = searchInfo.text.toString().trim()
            //empty input cannot start search
            if (TextUtils.isEmpty(info)) {
                Toast.makeText(this,"Nothing Input!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //start to search
            val result = ManageDB.searchBillList(info,userId)
            dataList.clear()
            dataList.addAll(result)
            adapter.notifyDataSetChanged()
        }
    }
}