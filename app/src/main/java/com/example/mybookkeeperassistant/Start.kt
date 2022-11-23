package com.example.mybookkeeperassistant

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

class Start : AppCompatActivity() {
    var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //click the done
        //get the records save in the database
        //back
//        supportActionBar!!.hide()
        if (Build.VERSION.SDK_INT <= 19) {
            setStatusBarColor4_4(this, ContextCompat.getColor(this, R.color.dark_grey) );
        } else {
            setStatusBarColor(this, ContextCompat.getColor(this, R.color.dark_grey) );
        }
        setContentView(R.layout.activity_start)
        handler.postDelayed(Runnable { gotoLogin() }, 4000)
    }

    private fun gotoLogin() {
        val intent = Intent(this@Start, Login::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        if (handler != null){
            //If token is null, all callbacks and messages will be removed.
            handler.removeCallbacksAndMessages(null)
        }
        super.onDestroy()
    }

    //cacel the physical back button
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarColor(activity: Activity, statusColor: Int) {
        val window: Window = activity.getWindow()
        //cancel the state bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //Add Flag to set the status bar to drawable mode
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        //Set the status bar color
        window.setStatusBarColor(statusColor)
        //Set the system status bar to be visible
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE)
        //Let the view not adjust its layout according to the system window
        val mContentView: ViewGroup = window.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
        val mChildView: View = mContentView.getChildAt(0)
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false)
            ViewCompat.requestApplyInsets(mChildView)
        }
    }

    fun setStatusBarColor4_4(activity: Activity, statusColor: Int) {
        val window: Window = activity.getWindow()
        val mContentView: ViewGroup = activity.findViewById(Window.ID_ANDROID_CONTENT) as ViewGroup
    }

}