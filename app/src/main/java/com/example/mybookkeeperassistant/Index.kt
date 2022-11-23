package com.example.mybookkeeperassistant

import android.app.LocalActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import frag_mains.ChartFragment
import frag_mains.DetailFragment
import frag_mains.HistoryFragment
import frag_mains.SettingFragment
import kotlinx.android.synthetic.main.activity_index.*
import java.util.*

class Index : FragmentActivity() {
    private lateinit var myPage:ViewPager

    lateinit var detailImg:ImageView
    lateinit var chartImg:ImageView
    lateinit var historyImg:ImageView

    var userId = -1




    private lateinit var clickDetail:RelativeLayout
    private lateinit var clickChart:RelativeLayout
    private lateinit var clickHistroy:RelativeLayout

    private lateinit var adapter:FragmentStatePagerAdapter
    private lateinit var pages:MutableList<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("hello","Create")
        requestWindowFeature(Window.FEATURE_NO_TITLE)


        setContentView(R.layout.activity_index)

        //reference7

        initView()

        //get the four tab

        //loadData
        loadData()
        setClick()







    }



    override fun onStop() {
        super.onStop()
        Log.d("onStop","onStop")
    }


    private fun setClick() {

        clickHistroy.setOnClickListener {
            myPage.setCurrentItem(2)
            historyImg.setImageResource(R.mipmap.calendar_a)

        }
        clickChart.setOnClickListener {
            myPage.setCurrentItem(1)
            chartImg.setImageResource(R.mipmap.chart)

        }
        clickDetail.setOnClickListener {
            myPage.setCurrentItem(0)
            detailImg.setImageResource(R.mipmap.detail)

        }
        //reference 7
        myPage.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            //reference 7
            override fun onPageSelected(position: Int) {
                //Get the current Tab of ViewPager
                val currentItem: Int = myPage.getCurrentItem()
                //Set all Buttons to gray
                initImage()
                when (currentItem) {
                    0 -> {
                        detailImg.setImageResource(R.mipmap.detail)
                        index_detail.setTextColor(getResources().getColor(R.color.blue))
                    }

                    1 -> {
                        chartImg.setImageResource(R.mipmap.chart)
                        index_chart.setTextColor(getResources().getColor(R.color.blue))
                    }
                    2 -> {
                        historyImg.setImageResource(R.mipmap.calendar_a)
                        index_history.setTextColor(getResources().getColor(R.color.blue))
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun initImage() {
        detailImg.setImageResource(R.mipmap.detail_na)
        chartImg.setImageResource(R.mipmap.chart_na)
        historyImg.setImageResource(R.mipmap.calendar_na)
        index_history.setTextColor(getResources().getColor(R.color.dark_grey))
        index_detail.setTextColor(getResources().getColor(R.color.dark_grey))
        index_chart.setTextColor(getResources().getColor(R.color.dark_grey))



    }

    //reference 7
    private fun loadData() {
        pages = java.util.ArrayList()
        val test = ChartFragment()
        val test2 = DetailFragment()
        val test3 = HistoryFragment()
        val detail = DetailFragment(this,userId,this)
        pages.add(detail)
        val chart = ChartFragment(this,userId)
        pages.add(chart)
        val history = HistoryFragment(this,userId)
        pages.add(history)


        //reference 7
        adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment { //Get the Fragment of the corresponding position from the collection
                return pages.get(position)
            }

            override fun getCount(): Int { //Get the total number of Fragments in the collection
                return pages.size
            }

        }
        //Set up the ViewPager adapter

        myPage.adapter = adapter


    }

    private fun initView() {
        userId = intent.getIntExtra("userID",-1)
        myPage = index_content
        clickChart = index_page2
        clickDetail = index_page1
        clickHistroy = index_page3
        chartImg = index_btn_chart
        detailImg = index_btn_detail
        historyImg = index_btn_history


    }
}