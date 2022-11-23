package record_frag

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import com.example.mybookkeeperassistant.R
import database.ManageDB
import database.TypeBean

class IncomeRecord (val context1: Context, userID: Int):BaseRecord(userID) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun loadTypeGrid() {
        super.loadTypeGrid()
        val incomes = ManageDB.getTypes(1)
        (typeList as ArrayList<TypeBean>).addAll(incomes)
        val levireBrushed = Typeface.createFromAsset(context1.assets, "LeviReBrushed.ttf")

        baseAdapter.notifyDataSetChanged()
        typeName.setText("Others")
        typeImage.setImageResource(R.mipmap.other_activate)
        typeName.setTypeface(levireBrushed)
        typeName.setTextColor(getResources().getColor(R.color.orange))
    }

    override fun saveAccountToDB() {
        super.saveAccountToDB()
        billBean.kind = 1
        ManageDB.insertToAccountTB(billBean,userID)
    }
}