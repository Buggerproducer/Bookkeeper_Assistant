package database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.ByteArrayOutputStream

import android.graphics.Bitmap
import com.example.mybookkeeperassistant.Register
import android.graphics.BitmapFactory

import android.graphics.drawable.BitmapDrawable
import com.example.mybookkeeperassistant.R


/*
* responsible for managing the database
* addition,deletion,modification,and search
* */
object ManageDB {
    lateinit var database:SQLiteDatabase

    //initialize the com.example.mybookkeeperassistant.db

    fun initializeDB(context: Context){
        //get the helper
        val dbHelper = OpenHelperDB(context)
        database = dbHelper.writableDatabase

    }

    //initialize the com.example.mybookkeeperassistant.db
    fun  getTypes(kind: Int):List<TypeBean>{
        val list: MutableList<TypeBean> = ArrayList()
        //read the type table
        val sql_str = "select * from typetable where kind =$kind"
        val cursor = database.rawQuery(sql_str,null)
        //loop
        while (cursor.moveToNext()) {
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val imageId = cursor.getInt(cursor.getColumnIndex("imageId"))
            val selectedImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"))
            val kind1 = cursor.getInt(cursor.getColumnIndex("kind"))
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typeBean = TypeBean(id, typename, imageId, selectedImageId, kind1)
            list.add(typeBean)
        }
        return list
    }

    fun insertToUser(
        userName: String,
        passWord: String,
        phone: String,
        email: String,
        register: Register,
        budget: Float,
        registerTime:String

    ){
        val values = ContentValues()
        val baos = ByteArrayOutputStream()
        val bitmap = (register.getResources().getDrawable(R.mipmap.default_icon) as BitmapDrawable).bitmap
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos)
        }
        values.put("username",userName)
        values.put("userIcon",baos.toByteArray())
        values.put("password",passWord)
        values.put("phone",phone)
        values.put("email",email)
        values.put("budget",budget)
        values.put("registerTime",registerTime)

        database.insert("usertable",null,values)
        Log.d("animee","insert to the usertable successfully")


    }
    fun updateUserBudget(budget: Float, userID: Int){
        val values=ContentValues()
        values.put("budget", budget)
        database.update("usertable",values, "id="+userID.toString(),null)

    }
    fun updateUserIcon(baos:ByteArrayOutputStream,userID: Int){
        val values=ContentValues()
        values.put("userIcon",baos.toByteArray())
        database.update("usertable",values, "id="+userID.toString(),null)
    }
    fun updateUserName(userName:String,userID: Int){
        val values=ContentValues()
        values.put("username", userName)
        database.update("usertable",values, "id="+userID.toString(),null)
    }
    fun getUserName(userID: Int): String? {
        val sql_str = "select * from usertable where id=? order by id desc"
        val cursor = database.rawQuery(sql_str, arrayOf(userID.toString()))
        if (cursor.moveToFirst()){
            val password_stroed = cursor.getString(cursor.getColumnIndex("username"))
            return password_stroed
        }
        return null
    }
    fun getRegisterTime(userID: Int): String? {
        val sql_str = "select * from usertable where id=? order by id desc"
        val cursor = database.rawQuery(sql_str, arrayOf(userID.toString()))
        if (cursor.moveToFirst()){
            val password_stroed = cursor.getString(cursor.getColumnIndex("registerTime"))
            return password_stroed
        }
        return null
    }

    fun getBudget(userID: Int):Float?{
        val sql_str = "select * from usertable where id=? order by id desc"
        val cursor = database.rawQuery(sql_str, arrayOf(userID.toString()))
        if (cursor.moveToFirst()){
            val password_stroed = cursor.getFloat(cursor.getColumnIndex("budget"))
            return password_stroed
        }
        return 0f
    }
    //insert a piece of data into accounttb
    fun insertToAccountTB(bean: BillBean,userID:Int){
        val values = ContentValues()
        values.put("typename",bean.typeName)
//        values.put("user_id",userID)
        values.put("user_id",userID)
        values.put("selectedImageId",bean.selectedImageId)
        values.put("note",bean.notes)
        values.put("money",bean.money)
        values.put("time",bean.dateTime)
        values.put("year",bean.year)
        values.put("month",bean.month)
        values.put("day",bean.day)
        values.put("kind",bean.kind)

        database.insert("accounttable",null,values)
        Log.d("animee","okkkkkkkkkkkk")


    }


    fun loginCheck(phone: String,passWord: String):Boolean{
        val sql_str = "select * from usertable where phone=? order by id desc"
        val cursor = database.rawQuery(sql_str, arrayOf(phone))
        if (cursor.moveToFirst()){
            val password_stroed = cursor.getString(cursor.getColumnIndex("password"))
            return passWord == password_stroed
        }
        return false
    }
    fun getBillNumber(userID: Int):Int{
        val sql = "select * from accounttable where user_id=? order by id desc"
        val cursor = database.rawQuery(sql, arrayOf(userID.toString()))
        var size=0
        while (cursor.moveToNext()){
            size++
        }
        return size

    }

    //get all bills on someday
    fun getOneDayTotalList(year:Int,month:Int,day:Int,userID: Int):List<BillBean>{
        val list = ArrayList<BillBean>()
        val sql = "select * from accounttable where year=? and month=? and day=? and user_id=? order by id desc"
        val cursor = database.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", day.toString() + "",userID.toString()+""))
        //traverse the selected data
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val notes = cursor.getString(cursor.getColumnIndex("note"))

            val sImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"));

            val billBean = BillBean(id,typename,notes,time,sImageId,money,year,month,day,kind)
            list.add(billBean)
        }
        return list
    }

    // get the total expense or income on someday. kind: expense:0, income:1
    fun getOneDayTotal(year: Int,month: Int,day: Int,kind: Int,userID: Int):Float{
        var sum = 0.0f
        val sql_str = "select sum(money) from accounttable where year=? and month=? and day=? and kind=? and user_id=?"
        val cursor = database.rawQuery(sql_str, arrayOf(year.toString() + "", month.toString() + "", day.toString() + "",kind.toString()+"",userID.toString()))
        //traverse only has one
        if (cursor.moveToFirst()){
            val money = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            sum = money
        }
        return sum
    }

    // get the total expense or income on one month. kind: expense:0, income:1
    fun getOneMonthTotal(year: Int,month: Int,kind: Int,userID: Int):Float{
        var sum = 0.0f
        val sql_str = "select sum(money) from accounttable where year=? and month=? and kind=? and user_id=?"
        val cursor = database.rawQuery(sql_str, arrayOf(year.toString() + "", month.toString() + "",kind.toString()+"",userID.toString()))
        //traverse only has one
        if (cursor.moveToFirst()){
            val money = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            sum = money
        }
        return sum
    }
    fun getOneMonthTotalNum(year: Int,month: Int,kind: Int,userID: Int):Int{
        var t_num = 0
        val sql_str = "select count(money) from accounttable where year=? and month=? and kind=? and user_id=?"
        val cursor = database.rawQuery(sql_str, arrayOf(year.toString() + "", month.toString() + "",kind.toString()+"",userID.toString()))
        //traverse only has one
        if (cursor.moveToFirst()){
            val num = cursor.getFloat(cursor.getColumnIndex("count(money)"))
            t_num = num.toInt()
        }
        return t_num
    }



    fun deleteItemFormAccount(clickId: Int):Int {
        val delete_item = database.delete("accounttable","id=?", arrayOf(clickId.toString()))
        return delete_item
    }

    //search
    fun searchBillList(info:String,userID: Int):List<BillBean>{
        val list = ArrayList<BillBean>()
        val sql_str = "select * from accounttable where note like '%"+info+"%'"+" and user_id="+userID
        val cursor = database.rawQuery(sql_str,null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val t_note = cursor.getString(cursor.getColumnIndex("note"))
            val selectedImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"));
            val billBean = BillBean(id,typename,t_note,time,selectedImageId,money,year,month,day,kind)
            list.add(billBean)
        }
        return list
    }

    //get all bills on someday
    fun getOneMonthTotalList(year:Int,month:Int,userID: Int):List<BillBean>{
        val list = ArrayList<BillBean>()
        val sql = "select * from accounttable where year=? and month=? and user_id=? order by id desc"
        val cursor = database.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "",userID.toString()))
        //traverse the selected data
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val notes = cursor.getString(cursor.getColumnIndex("note"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))

            val sImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"));

            val billBean = BillBean(id,typename,notes,time,sImageId,money,year,month,day,kind)
            list.add(billBean)
        }
        return list
    }

    //count how many years are there in db
    fun getAllYears(userID: Int):List<Int>{
        val years = ArrayList<Int>()
        val sql_str = "select distinct(year) from accounttable where user_id=? order by year asc "
        val cursor = database.rawQuery(sql_str, arrayOf(userID.toString()))
        var i = 0
        while (cursor.moveToNext()){
            val year = cursor.getInt(cursor.getColumnIndex("year"))
//            Log.d("year"+i, year.toString())
            i++

            years.add(year)
        }
        return years
    }

    fun getAllMonth(userID: Int,year: Int):List<Int>{
        val years = ArrayList<Int>()
        val sql_str = "select distinct(month) from accounttable where user_id=? and year=? order by month asc "
        val cursor = database.rawQuery(sql_str, arrayOf(userID.toString(),year.toString()))
        var i = 0
        while (cursor.moveToNext()){
            val year = cursor.getInt(cursor.getColumnIndex("month"))
//            Log.d("year"+i, year.toString())
            i++

            years.add(year)
        }
        return years
    }

    fun getMonthBill(userID: Int,year: Int):List<MonthBillBean>{
        val list = ArrayList<MonthBillBean>()
        val month_list = getAllMonth(userID,year)
        for (index in 0..(month_list.size-1)){
            val month = month_list.get(index)
            val monthBillBean = MonthBillBean(month,
                getOneMonthTotal(year,month,0,userID),
            getOneMonthTotal(year,month,1,userID))
            list.add(monthBillBean)
        }
        return list
    }


    //delete all from accounttb
    fun deleteAll(){
        val sql_str = "delete from accounttable"
        database.execSQL(sql_str)
    }

    //get analysis list data
    fun getAnalysisListData(year: Int,month: Int,kind: Int,userID: Int):List<AnalysisBean>{
        val list = ArrayList<AnalysisBean>()
        val sumOfMonth:Float = getOneMonthTotal(year,month,kind,userID)
        val sql_str = "select typeName,selectedImageId,sum(money) as total from accounttable where year=? and month=? and kind=? and user_id=? group by typeName order by total desc"
        val myCursor = database.rawQuery(sql_str,arrayOf(year.toString() + "", month.toString() + "",kind.toString()+"",userID.toString()))
        while (myCursor.moveToNext()){
            val selectedID = myCursor.getInt(myCursor.getColumnIndex("selectedImageId"))
            val typeName = myCursor.getString(myCursor.getColumnIndex("typename"))
            val total = myCursor.getFloat(myCursor.getColumnIndex("total"))
            val str :String = String.format("%.4f",(total/sumOfMonth))
            val ratio = str.toFloat()
            val ab = AnalysisBean(selectedID,typeName,ratio,total)
            list.add(ab)
        }
        return list
    }

    //get the max of one month
    fun getMaxOneMonth(year: Int,month: Int,kind: Int,userID: Int):Float{
        val sql_str = "select sum(money) as total from accounttable where year=? and month=? and kind=? and user_id=? group by day order by total desc"
        val myCursor = database.rawQuery(sql_str,arrayOf(year.toString() + "", month.toString() + "",kind.toString()+"",userID.toString()))
        if (myCursor.moveToFirst()){
            val max = myCursor.getFloat(myCursor.getColumnIndex("total"))
            return max
        }
        return 0f;
    }

    //get total of one day
    fun getEveryDayOfMonth(year: Int,month: Int,kind: Int,userID: Int):List<BarBean>{
        val list =ArrayList<BarBean>()
        val sql_str = "select day,sum(money) from accounttable where year=? and month=? and kind=? and user_id=? group by day"
        val cursor = database.rawQuery(sql_str,arrayOf(year.toString() + "", month.toString() + "",kind.toString()+"",userID.toString()))
        while (cursor.moveToNext()){
            val total = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            val date = cursor.getInt(cursor.getColumnIndex("day"))
            val ab = BarBean(year,month,date,total)
            list.add(ab)
        }
        return list
    }

    fun getTotalDetail(name:String,year: Int,month: Int,userID: Int):List<BillBean>{
        val list = ArrayList<BillBean>()
        val sql_str = "select * from accounttable where year=? and month=? and typename=? and user_id=? order by id desc"
        val cursor = database.rawQuery(sql_str,arrayOf(year.toString() + "", month.toString() + "",name+"",userID.toString()))

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val notes = cursor.getString(cursor.getColumnIndex("note"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))

            val sImageId = cursor.getInt(cursor.getColumnIndex("selectedImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"));

            val billBean = BillBean(id,typename,notes,time,sImageId,money,year,month,day,kind)
            list.add(billBean)
        }

        return list
    }

    fun getUserID(phone: String):Int{
        val sql_str = "select * from usertable where phone=? order by id desc"
        val cursor = database.rawQuery(sql_str, arrayOf(phone))
        if (cursor.moveToFirst()){
            return cursor.getInt(cursor.getColumnIndex("id"))
        }
        return -1
    }

    fun checkRepeatPhone(phone:String):Boolean{
        val sql_str = "select * from usertable where phone=? order by id desc"
        val cursor = database.rawQuery(sql_str, arrayOf(phone))
        var size = 0
        while (cursor.moveToNext()){
            size = size+1
        }
        return size < 1

    }

    fun getUserIcon(userID: Int): Bitmap? {
        val sql_str = "select * from usertable where id=? order by id desc"
        val cursor = database.rawQuery(sql_str, arrayOf(userID.toString()))
        if (cursor.moveToFirst()){
            val userIcon:ByteArray = cursor.getBlob(cursor.getColumnIndex("userIcon"))
            val bmpout = BitmapFactory.decodeByteArray(userIcon,0,userIcon.size)
            return bmpout
        }
        return null
    }




}