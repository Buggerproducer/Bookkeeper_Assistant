package frag_mains

import MyAdapater.BillsAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.mybookkeeperassistant.ChartAnalysis
import com.example.mybookkeeperassistant.R
import com.example.mybookkeeperassistant.Record
import com.example.mybookkeeperassistant.Search
import database.BillBean
import database.ManageDB
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_header.*
import utils.BudgetDialog
import utils.SettingDialog
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var todayList: ListView
    lateinit var dataList:MutableList<BillBean?>
    lateinit var adapter: BillsAdapter
    lateinit var recordButton:Button
    lateinit var settingButton:ImageView
    lateinit var searchButton:ImageView
    var year:Int?=null
    var day:Int?=null
    var month:Int?=null
    lateinit var header:View
    lateinit var headerExpense: TextView
    lateinit var headerIncome: TextView
    lateinit var headerEye: ImageView
    lateinit var headerBudget: TextView
    lateinit var headerTodayTotal: TextView
    var isShow:Boolean = true
    lateinit var fga:FragmentActivity
    var userID = -1
    lateinit var t:Context
    constructor(t:Context,userID:Int,fga:FragmentActivity):this(){
        this.userID = userID
        this.fga = fga
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
        val view = inflater.inflate(R.layout.activity_main, container, false)
        initializedTime()

        searchButton = view.findViewById(R.id.main_search)
        recordButton = view.findViewById(R.id.record_btn)
        settingButton = view.findViewById(R.id.main_setting)
//        moreButton = view.findViewById(R.id.more_btn)
        settingButton.setOnClickListener{
            val settingDialog = SettingDialog(t,fga,userID)
            settingDialog.show()
            settingDialog.setDialogSize()
        }
        searchButton.setOnClickListener{
            val intent = Intent(t, Search::class.java)
            intent.putExtra("user_id",userID)
            startActivity(intent)
        }

        Log.d("detail_create","Create")



        recordButton.setOnClickListener{
            //jump to the record interface
            val intent = Intent(t, Record::class.java)
            intent.putExtra("user_id",userID)
            startActivity(intent)
        }
//        moreButton.setOnClickListener{
//            //show more dialog
//            val moreDialog = MoreDialog(t)
//            moreDialog.show()
//            moreDialog.setDialogSize()
//        }
        todayList = view.findViewById(R.id.main_list)

        //add the header of the listview
        addHeader(view)
        //get the list data
        dataList = java.util.ArrayList()
        adapter = BillsAdapter(t,dataList);
        todayList.adapter = adapter
        setListListener()

        val levireBrushed = Typeface.createFromAsset(t.assets,"LeviReBrushed.ttf")
        val three = Typeface.createFromAsset(t.assets,"3.ttf")
        val alan_font = Typeface.createFromAsset(t.assets,"Alan Font.otf")
        val englishling = Typeface.createFromAsset(t.assets,"EnglishLing.ttf")
        val Sketchy = Typeface.createFromAsset(t.assets,"Sketchy.ttf")
        val title:TextView = view.findViewById(R.id.app_title)
        val textChart:TextView = view.findViewById(R.id.header_chart)
        val textExpense:TextView = view.findViewById(R.id.header_expense_label)
        val textBudget:TextView = view.findViewById(R.id.header_budget_label)
        val textIncome:TextView = view.findViewById(R.id.header_income_label)
        val textTotal:TextView = view.findViewById(R.id.header_today_total)

        title.setTypeface(levireBrushed)
        textChart.setTypeface(levireBrushed)
        textExpense.setTypeface(Sketchy)
        textBudget.setTypeface(Sketchy)
        textIncome.setTypeface(Sketchy)
//
        textTotal.setTypeface(three)
//
//
//
//
        headerExpense.setTypeface(Sketchy)
        headerIncome.setTypeface(Sketchy)
        headerBudget.setTypeface(Sketchy)
        loadTodayList()
        headerShow()
        longClickDelete()

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters

    }
    private fun setListListener() {
        todayList.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
                if (i == 0) {
                    return@OnItemLongClickListener false//header is not clickable
                }
                val pos = i - 1
                val clickBean = dataList.get(pos)
                showDeleteItemDialog(clickBean)
                return@OnItemLongClickListener false
            }
    }
    private fun longClickDelete(){
        todayList.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
                if (i == 0) {
                    return@OnItemLongClickListener false//header is not clickable
                }
                val pos = i - 1
                val click = dataList.get(pos)
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
                    headerShow(); //change the textview of the header
                }

            }
        builder.create().show() //make the dialog visible


    }
    private fun initializedTime() {
        val calendar = Calendar.getInstance()
        year = calendar[Calendar.YEAR]
        month = calendar[Calendar.MONTH] + 1
        day = calendar[Calendar.DAY_OF_MONTH]
    }
    private fun addHeader(view: View) {
        //conver the layout to the view object
        header = layoutInflater.inflate(R.layout.main_header,null)//null means no subcomponent
        todayList.addHeaderView(header)
        //find the avaliable controller in the header
        headerExpense = view.findViewById(R.id.header_expense_number)
        headerIncome = view.findViewById(R.id.header_income_number)
        headerTodayTotal = view.findViewById(R.id.header_today_total)
        headerBudget = view.findViewById(R.id.header_budget_number)
        headerEye = view.findViewById(R.id.header_eye)


        //set the click event
        headerBudget.setOnClickListener{
            showBudget()

        }
        val chart:TextView = view.findViewById(R.id.header_chart)
        chart.setOnClickListener {
            val intent = Intent(t, ChartAnalysis::class.java)
            intent.putExtra("userID",userID)
            startActivity(intent)
        }
        header.setOnClickListener{

        }
        headerEye.setOnClickListener{
            //convert between explicit text and implicit text
            hideText()
        }

    }

    private fun hideText() {
        if (isShow){
            //explicit->implicit
            val passowrdMethod = PasswordTransformationMethod.getInstance()
            headerIncome.transformationMethod = passowrdMethod
            headerBudget.transformationMethod = passowrdMethod
            headerExpense.transformationMethod = passowrdMethod
            headerEye.setImageResource(R.mipmap.no_eye)
            isShow = false;
        }else{

            val passowrdMethod = HideReturnsTransformationMethod.getInstance()
            headerIncome.transformationMethod = passowrdMethod
            headerBudget.transformationMethod = passowrdMethod
            headerExpense.transformationMethod = passowrdMethod
            headerEye.setImageResource(R.mipmap.eye)
            isShow = true;
        }
    }

    override fun onResume() {
        super.onResume()
        loadTodayList()
        headerShow()
        Log.d("main_resume","Resume")
    }
    private fun headerShow() {
        //get the today expense and income, display in the view
        val incomeOneDay = year?.let { month?.let { it1 -> day?.let { it2 ->
            ManageDB.getOneDayTotal(it, it1,
                it2,1,userID)
        } } }
        val outcomeOneDay = year?.let { month?.let { it1 -> day?.let { it2 ->
            ManageDB.getOneDayTotal(it, it1,
                it2,0,userID)
        } } }

        //set the textview
        val infoOneDay = "Today Expense $ "+outcomeOneDay+"  Income $ "+incomeOneDay
        headerTodayTotal.setText(infoOneDay)

        val incomeOneMonth = year?.let { month?.let { it1 -> ManageDB.getOneMonthTotal(it, it1,1,userID) } }
        val outcomeOneMonth = year?.let { month?.let { it1 -> ManageDB.getOneMonthTotal(it, it1,0,userID) } }



        headerIncome.setText("$"+incomeOneMonth)
        headerExpense.setText("$"+outcomeOneMonth)

        //display the rest budget
        val budget_money = ManageDB.getBudget(userID)
        if (ManageDB.getBudget(userID)==0f) {
            headerBudget.setText("$0")
        }else{
            val rest = budget_money?.minus(outcomeOneMonth!!)
            headerBudget.setText("$"+rest)
        }
    }
    private fun loadTodayList() {
        val list = year?.let { month?.let { it1 -> day?.let { it2 ->
            ManageDB.getOneDayTotalList(it, it1,
                it2
            ,userID)
        } } }
        dataList.clear()
        if (list != null) {
            dataList.addAll(list)
        }
        adapter.notifyDataSetChanged()
    }

    private fun showBudget() {
        val dialog = BudgetDialog(t);
        dialog.show()
        dialog.setDialogSize()
        dialog.onEnsureListener = object : BudgetDialog.OnEnsureListener {
            override fun onEnsure(money: Float) {
                //put the money into the shared params, store


                //calculate the rest
                val monthMoney = year?.let { month?.let { it1 -> ManageDB.getOneMonthTotal(it, it1,0,userID) } }
                val rest = money - monthMoney!!
                headerBudget.setText("$"+rest)
                ManageDB.updateUserBudget(money,userID)
            }

        }
    }
}