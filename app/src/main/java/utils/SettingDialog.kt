package utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.FragmentActivity
import com.example.mybookkeeperassistant.ChartAnalysis
import com.example.mybookkeeperassistant.R
import com.example.mybookkeeperassistant.Setting
import com.example.mybookkeeperassistant.UserInfo
import kotlinx.android.synthetic.main.more.*

class SettingDialog (context: Context,val fga:FragmentActivity,val userID:Int): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more)
        setting_user.setOnClickListener {
            val intent = Intent(context, UserInfo::class.java)
            intent.putExtra("userID",userID)
            context.startActivity(intent)
        }
        more_setting.setOnClickListener {
            val intent = Intent(context, Setting::class.java)
            context.startActivity(intent)
        }
        setting_logout.setOnClickListener {
            fga.finish()
        }

    }

    fun setDialogSize() {
        //get window object
        val window = window
        //get the param of the object
        val wlp = window!!.attributes
        //get the width of the screen
        val d = window.windowManager.defaultDisplay
        wlp.width = d.width //dialog width = screen width
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.white)
        window.attributes = wlp
    }
}
