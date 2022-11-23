package MyAdapater

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mybookkeeperassistant.R
import database.MonthBillBean

class MonthBillAdapter(var context: Context, var dataList: MutableList<MonthBillBean>) : BaseAdapter() {
    lateinit var inflater: LayoutInflater
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(p0: Int): MonthBillBean? {
        return dataList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    internal inner class ViewHolder(view: View?){
        lateinit var monthTv: TextView
        lateinit var incomeTv: TextView
        lateinit var expenseTv: TextView

        init {
            if (view != null) {
                monthTv = view.findViewById(R.id.item_user_bill_month)
                incomeTv = view.findViewById(R.id.item_user_bill_income)
                expenseTv = view.findViewById(R.id.item_user_bill_expense)
            }

        }

    }

    init {
        inflater = LayoutInflater.from(context)


    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        var view = view
        var holder: MonthBillAdapter.ViewHolder? = null
        val three = Typeface.createFromAsset(context.assets, "3.ttf")
        val Sketchy = Typeface.createFromAsset(context.assets, "Sketchy.ttf")

        //initialize the holder
        if (view == null){
            view = inflater.inflate(R.layout.item_month_bill,viewGroup,false)
            holder = ViewHolder(view)
            view.tag = holder
        }
        kotlin.run {
            holder =
                view?.tag as MonthBillAdapter.ViewHolder
        }
        val b = dataList[i]
        if (b != null) {
            holder?.monthTv?.setText(b.month_str)
            holder?.expenseTv?.setText(b.income.toString())
            holder?.incomeTv?.setText(b.expense.toString())

        }
        return view
    }

}