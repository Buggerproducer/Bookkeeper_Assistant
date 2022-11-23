package database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mybookkeeperassistant.R

class OpenHelperDB(context: Context?):
    SQLiteOpenHelper(context,"bookkeeper.com.example.mybookkeeperassistant.db",null,1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        var sql_str = "create table typetable(id integer primary key autoincrement,typename varchar(10),imageId integer,selectedImageId integer,kind integer)"
        p0?.execSQL(sql_str)
        if (p0 != null) {
            insertType(p0)
        }

        //create the user table
        sql_str = "create table usertable(id integer primary key autoincrement,username varchar(10),"+
                "userIcon BLOB,password varchar(80),phone varchar(20),email varchar(20),budget float,registerTime varchar(60))"
        ""
        p0?.execSQL(sql_str)

        //create the record table
        sql_str =
            "create table accounttable(id integer primary key autoincrement,user_id integer,typename varchar(10)," +
                    "selectedImageId integer,note varchar(80),money float,time varchar(60),year integer,month integer,day integer,kind integer,"+
                    "CONSTRAINT accounttable_user_id_fk FOREIGN KEY(user_id) REFERENCES usertable(id))"
        p0?.execSQL(sql_str)






    }

    //insert data into the com.example.bookkeeperassistant.db
    private fun insertType(sqLiteDatabase: SQLiteDatabase) {
        val sql = "insert into typetable(typename,imageId,selectedImageId,kind) values (?,?,?,?)"
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Others", R.mipmap.other, R.mipmap.other_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Food", R.mipmap.meal, R.mipmap.meal_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Transport", R.mipmap.transport, R.mipmap.transport_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Shop", R.mipmap.shopping, R.mipmap.shopping_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Dress", R.mipmap.dress, R.mipmap.dress_active, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Necessity", R.mipmap.utils, R.mipmap.utils_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Entertain", R.mipmap.entertain, R.mipmap.entertain_active, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Snacks", R.mipmap.snack, R.mipmap.snack_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Liq&Tob", R.mipmap.alchol, R.mipmap.alchol_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Study", R.mipmap.study, R.mipmap.study_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Medical", R.mipmap.medical, R.mipmap.medical_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Housing", R.mipmap.house, R.mipmap.house_activate, 0)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Comm.", R.mipmap.commu, R.mipmap.commu_activate, 0)
        )
        sqLiteDatabase.execSQL(sql, arrayOf<Any>("Gifts", R.mipmap.gift, R.mipmap.gift_activate, 0))
        sqLiteDatabase.execSQL(sql, arrayOf<Any>("Others", R.mipmap.other, R.mipmap.other_activate, 1))
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Salary", R.mipmap.salary, R.mipmap.salary_activate, 1)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Bonus", R.mipmap.bonus, R.mipmap.bonus_activate, 1)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Drawback", R.mipmap.lend, R.mipmap.lend_activate, 1)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Debt", R.mipmap.debt, R.mipmap.debt_activate, 1)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("interest", R.mipmap.interest, R.mipmap.interest_activate, 1)
        )
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Investment", R.mipmap.investment, R.mipmap.investment_activate, 1)
        )
        //        sqLiteDatabase.execSQL(sql,new Object[]{"", R.mipmap,R.mipmap,1});
        sqLiteDatabase.execSQL(
            sql,
            arrayOf<Any>("Windfall", R.mipmap.accident, R.mipmap.accident_activate, 1)
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}