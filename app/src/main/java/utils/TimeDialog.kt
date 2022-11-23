package utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.example.mybookkeeperassistant.R
import kotlinx.android.synthetic.main.calendar.*

class TimeDialog(context: Context): Dialog(context),View.OnClickListener {
    lateinit var hour:EditText
    lateinit var min:EditText
    lateinit var date:DatePicker
    lateinit var doneBtn:Button
    lateinit var cancelBtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)
        hour = calendar_hour
        min = calendar_min
        date = calendar_date
        doneBtn = calendar_done
        cancelBtn = calendar_cancel
        doneBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)
        hideHeader()
    }
    interface OnEnsureListener {
        fun onEnsure(time: String?, year: Int, month: Int, day: Int)
    }

    var onEnsureListener: OnEnsureListener? = null
    @JvmName("setOnEnsureListener1")
    fun setOnEnsureListener(onEnsureListener: OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.calendar_cancel -> cancel()
            R.id.calendar_done -> {
                val year = date!!.year
                val month = date!!.month + 1 //month is saved as 0,1.. so we plus 1
                val dayOfMonth = date!!.dayOfMonth
                //make sure the format is 01,02.....
                var monthStr = month.toString()
                if (month < 10) {
                    monthStr = "0$month"
                }
                var dayStr = dayOfMonth.toString()
                if (month < 10) {
                    dayStr = "0$dayOfMonth"
                }
                //get the hour and min
                var hourStr = hour!!.text.toString()
                var minStr = min!!.text.toString()
                //it can be youhua
                var hour = 0
                if (!TextUtils.isEmpty(hourStr)) {
                    hour = hourStr.toInt()
                    hour = hour % 24 //correct the other wrong format
                }
                var min = 0
                if (!TextUtils.isEmpty(minStr)) {
                    min = minStr.toInt()
                    min = min % 60
                }
                hourStr = hour.toString()
                minStr = min.toString()
                if (hour < 10) {
                    hourStr = "0$hourStr"
                }
                if (min < 10) {
                    minStr = "0$minStr"
                }
                val timeForm = "$year-$monthStr-$dayStr $hourStr:$minStr"
                if (onEnsureListener != null) {
                    onEnsureListener!!.onEnsure(timeForm, year, month, dayOfMonth)
                }
                cancel()
            }
        }
    }

    private fun hideHeader(){
        val root = date!!.getChildAt(0) as ViewGroup
            ?: return //get the first chile(ie. header)
        val header = root.getChildAt(0) ?: return
        var headerId = context.resources.getIdentifier("date_picker_header", "id", "android")
        if (headerId == header.id) {
            header.visibility = View.GONE
        }
    }
}