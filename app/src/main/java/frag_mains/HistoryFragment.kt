package frag_mains

import MyAdapater.BillsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.mybookkeeperassistant.R
import database.BillBean
import database.ManageDB
import kotlinx.android.synthetic.main.activity_history.*
import utils.CalendarDialog
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var time: TextView
    lateinit var history: ListView
    lateinit var dataList:MutableList<BillBean?>
    lateinit var adapter: BillsAdapter
    lateinit var calendar: ImageView
    var selectedYear = -1
    var selectedMonth = -1
    var year=0
    var month=0
    var day=0
    var userID=-1
    lateinit var t:FragmentActivity
    constructor(t:FragmentActivity,userID:Int):this(){
        this.userID = userID
        this.t = t
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mview = inflater.inflate(R.layout.activity_history, container, false)
        time = mview.findViewById(R.id.history_date)
        history = mview.findViewById(R.id.history_list)
        calendar = mview.findViewById(R.id.history_calendar)
        dataList = java.util.ArrayList()
        adapter = BillsAdapter(t,dataList)
        history.adapter = adapter
        calendar.setOnClickListener{
            showCalendarDialog()
        }
        val back:ImageView = mview.findViewById(R.id.history_back)
        back.setOnClickListener{
            t.finish()
        }
        setCurrentTime()
        time.text = year.toString() +"."+month.toString()
        loadHistory(year,month)
        longClickDelete()
        return mview
    }
    private fun showCalendarDialog() {
//        Log.d("beforeseYear",selectedYear.toString())
//        Log.d("beforeseMonth",selectedMonth.toString())

        val dialog = CalendarDialog(t,selectedYear,selectedMonth,userID)
        dialog.show()
        dialog.setDialogSize()
        dialog.updateHistory = object : CalendarDialog.UpdateHistory {
            override fun update(sele: Int, year: Int, month: Int) {
                time.setText(year.toString()+"-"+month.toString())
                loadHistory(year,month)

                selectedYear = sele
                selectedMonth = month
//                Log.d("seYear",selectedYear.toString())
//                Log.d("seMonth",selectedMonth.toString())

            }

        }


    }

    private fun longClickDelete(){
        history.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
                val click = dataList.get(i)
                showDeleteItemDialog(click)
                return@OnItemLongClickListener false
            }
    }

    private fun showDeleteItemDialog(clickBean: BillBean?) {
        val builder = AlertDialog.Builder(t)
        builder.setTitle("Warning").setMessage("Are you sure to delete this records")
            .setNegativeButton("Cancel", null)
            .setPositiveButton(
                "Sure"
            ) { dialogInterface, i ->
                val clickId = clickBean?.id
                if (clickId != null) {
                    ManageDB.deleteItemFormAccount(clickId)
                    dataList.remove(clickBean)//real time update
                    adapter.notifyDataSetChanged() //remind adapter upates
                }

            }
        builder.create().show() //make the dialog visible


    }

    //load history
    private fun loadHistory(y:Int,m:Int) {
        val list = ManageDB.getOneMonthTotalList(y,m,userID)
        dataList.clear()
        dataList.addAll(list)
        adapter.notifyDataSetChanged()
    }
    //initialize the time
    private fun setCurrentTime() {
        val date = Date()
        val d = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val dateTime = d.format(date)
        time.text = dateTime
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        this.year = year
        this.month = month
        this.day = day
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
    }
}