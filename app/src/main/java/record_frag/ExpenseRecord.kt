package record_frag

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import com.example.mybookkeeperassistant.R
import database.ManageDB
import database.TypeBean

class ExpenseRecord(val context1: Context, userID: Int):BaseRecord(userID) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadTypeGrid() {
        super.loadTypeGrid()
        val expenses = ManageDB.getTypes(0)
        (typeList as ArrayList<TypeBean>).addAll(expenses)
        val levireBrushed = Typeface.createFromAsset(context1.assets,"LeviReBrushed.ttf")

        baseAdapter.notifyDataSetChanged()
        typeName.setText("Others")
        typeImage.setImageResource(R.mipmap.other_activate)
        typeName.setTypeface(levireBrushed)
        typeName.setTextColor(getResources().getColor(R.color.blue))
    }

    override fun saveAccountToDB() {
        super.saveAccountToDB()
        billBean.kind = 0
        ManageDB.insertToAccountTB(billBean,userID)
    }

}