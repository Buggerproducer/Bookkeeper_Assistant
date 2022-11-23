package com.example.mybookkeeperassistant

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import database.ManageDB
import kotlinx.android.synthetic.main.activity_user_info.*
import utils.ChangeNameDialog
import utils.NoteDialog
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import com.github.mikephil.charting.data.PieData

import java.util.ArrayList

import com.github.mikephil.charting.data.PieDataSet

import com.github.mikephil.charting.data.PieEntry
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class UserInfo : AppCompatActivity() {

    lateinit var userIcon:ImageView
    lateinit var userName:TextView
    lateinit var registerDays:TextView
    lateinit var totalBillNumber:TextView
    lateinit var billMonth:TextView
    lateinit var changeIconBtn:Button
    lateinit var changeNameBtn:Button
    lateinit var billIncome:TextView
    lateinit var billExpense:TextView
    lateinit var billRest:TextView
    lateinit var budgetPie:PieChart
    lateinit var budgetRest:TextView
    lateinit var budgetIncome:TextView
    lateinit var budgetBudget:TextView
    lateinit var iconBM:Bitmap
    var month=0
    var year = 0
    var userID=0
    lateinit var month_str:String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        initView()
        getCurrentMonth()
        loadHeaderInfo()
        loadBillInfo()
        loadBudgetInfo()
        loadPieData()
    }

    private fun loadPieData() {
        val strings: MutableList<PieEntry> = ArrayList()
        val rest = ManageDB.getBudget(userID)?.minus(ManageDB.getOneMonthTotal(year,month,0,userID))
        rest?.let { PieEntry(it,"Rest Budget") }?.let { strings.add(it) }
        strings.add(PieEntry(ManageDB.getOneMonthTotal(year,month,0,userID), "Expense"))

        val dataSet = PieDataSet(strings, "")

        val colors = ArrayList<Int>()
        colors.add(resources.getColor(R.color.orange))
        colors.add(resources.getColor(R.color.blue))
        dataSet.colors = colors

        val pieData = PieData(dataSet)
        pieData.setDrawValues(true)
        val description:Description = Description()
        description.text = ""
        budgetPie.description = description
        budgetPie.setEntryLabelColor(R.color.dark_grey)
        budgetPie.setEntryLabelTextSize(10f)

        budgetPie.setData(pieData)
        budgetPie.invalidate()

    }

    private fun loadBudgetInfo() {
        val month_expense = ManageDB.getOneMonthTotal(year,month,1,userID)
        val month_income = ManageDB.getOneMonthTotal(year,month,0,userID)
        val restBudget = ManageDB.getBudget(userID)
        if (restBudget != null) {
            if (restBudget-month_income<=0f){
                budgetRest.setText("$"+0)
            }else{
                budgetRest.setText("$"+(restBudget-month_income))
            }
        }

        budgetBudget.setText("$"+restBudget)
        budgetIncome.setText("$"+month_expense)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadHeaderInfo() {
        iconBM = ManageDB.getUserIcon(userID)!!
        if (iconBM != null){
            val round_bm= BitmapUtil.toRoundBitmap(iconBM)
            userIcon.setImageBitmap(round_bm)
        }
        val register_str = ManageDB.getRegisterTime(userID)
        val register_date = LocalDate.parse(register_str, DateTimeFormatter.ISO_DATE)
        val today_date = LocalDate.now()
        val difference = ChronoUnit.DAYS.between(register_date,today_date)
        registerDays.setText(difference.toString())
        totalBillNumber.setText(ManageDB.getBillNumber(userID).toString())




    }

    private fun loadBillInfo() {
        val month_expense = ManageDB.getOneMonthTotal(year,month,1,userID)
        val month_income = ManageDB.getOneMonthTotal(year,month,0,userID)
        val budget = ManageDB.getBudget(userID)
        val rest = budget?.minus(month_income)
        billExpense.setText("$"+month_expense)
        billIncome.setText("$"+month_income)
        if (rest != null) {
//            if (rest <=0){
//                billRest.setText("$"+0)
//            }else{
                billRest.setText("$"+rest)
//            }
        }


    }


    private fun getCurrentMonth() {
        year = Calendar.getInstance().get(Calendar.YEAR)
        month = Calendar.getInstance().get(Calendar.MONTH)+1
        when(month){
            1 -> month_str = "Jan"
            2 -> month_str = "Feb"
            3 -> month_str = "Mar"
            4 -> month_str = "Apr"
            5 -> month_str = "May"
            6 -> month_str = "Jun"
            7 -> month_str = "Jul"
            8 -> month_str = "Aug"
            9 -> month_str = "Sept"
            10 -> month_str = "Oct"
            11 -> month_str = "Nov"
            12 -> month_str = "Dec"
        }
        billMonth.setText(month_str)

    }
    private fun showChangeNameDialog() {
        val dialog = ChangeNameDialog(this)
        dialog?.setSize();//set the dialog size

        if (dialog != null) {
            dialog.onEnsureListener = object : ChangeNameDialog.OnEnsureListener {
                override fun onEnusre() {
                    val msg = dialog.editText
                    if (!TextUtils.isEmpty(msg)) {
                        ManageDB.updateUserName(msg,userID)
                        userName.setText(msg)
                    }
                }
            }
        }
        dialog?.show();
    }


    private fun initView() {
        userIcon = user_icon
        userName = user_name
        userID = intent.getIntExtra("userID",-1)
        changeIconBtn = user_change_icon
        changeNameBtn = user_change_name
        totalBillNumber = user_bills_number
        billMonth = user_bill_month
        billIncome = user_bill_income
        billExpense = user_bill_expense
        billRest = user_bill_rest
        budgetPie = user_budget_chart
        budgetIncome = user_budget_income
        budgetBudget = user_budget_budget
        budgetRest = user_rest_budget_number
        registerDays = user_totalday
        userName.setText(ManageDB.getUserName(userID))
        val levireBrushed = Typeface.createFromAsset(assets,"LeviReBrushed.ttf")
        val three = Typeface.createFromAsset(assets,"3.ttf")
        val alan_font = Typeface.createFromAsset(assets,"Alan Font.otf")
        val englishling = Typeface.createFromAsset(assets,"EnglishLing.ttf")
        val Sketchy = Typeface.createFromAsset(assets,"Sketchy.ttf")
        userName.setTypeface(three)
        totalBillNumber.setTypeface(three)
        registerDays.setTypeface(three)
        billMonth.setTypeface(Sketchy)
        billIncome.setTypeface(Sketchy)
        billExpense.setTypeface(Sketchy)
        billRest.setTypeface(Sketchy)
        budgetIncome.setTypeface(englishling)
        budgetBudget.setTypeface(englishling)
        budgetRest.setTypeface(englishling)
        month_bill.setOnClickListener{
            val intent = Intent(this,MonthBill::class.java)
            intent.putExtra("userID",userID)
            intent.putExtra("year",year)
            startActivity(intent)
        }
        changeIconBtn.setOnClickListener {
            val intent:Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent,"Choose photo"),0x00)
        }
        changeNameBtn.setOnClickListener {
            showChangeNameDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0x00){
            if (data != null) {
                handle(resultCode,data)
                val round_img = BitmapUtil.toRoundBitmap(iconBM)
                val baos = ByteArrayOutputStream()
                if (round_img != null) {
                    round_img.compress(Bitmap.CompressFormat.PNG,100,baos)
                }
                ManageDB.updateUserIcon(baos,userID)
                  userIcon.setImageBitmap(round_img)
            }
        }
    }
    private fun handle(resultCode: Int, data: Intent) {
        if (resultCode == RESULT_OK) { //结果代码是Ok的
            val uri: Uri? = data.data
            if (uri != null && data.data != null) {
                Log.i("TAG", "uri and data.getData is not empty")
                val contentResolver: ContentResolver = this.contentResolver
                if (iconBM != null) {
                    iconBM.recycle()
                }
                try {
                    iconBM = BitmapFactory.decodeStream(contentResolver.openInputStream(uri)) //出错
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            } else {
                Log.i("TAG", "uri为空或者data为空   " + "数据：" + data.data + "  uri: " + uri)
            }
        }
    }
}