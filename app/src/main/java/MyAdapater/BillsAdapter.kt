package MyAdapater

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mybookkeeperassistant.R
import database.BillBean
import java.lang.String
import java.util.*

class BillsAdapter(var context: Context, var dataList: MutableList<BillBean?>) : BaseAdapter() {
    lateinit var inflater: LayoutInflater
    var year = 0
    var month = 0
    var day = 0

    internal inner class ViewHolder(view: View?){
        var typeImage: ImageView
        var typeName:TextView
        var notes:TextView
        var dateTime:TextView
        var money:TextView
        var item:RelativeLayout
        init {
            typeImage = view?.findViewById(R.id.item_type)!!
            typeName = view.findViewById(R.id.item_typeName)
            notes = view.findViewById(R.id.item_notes)
            dateTime = view.findViewById(R.id.item_dateTime)
            money = view.findViewById(R.id.item_money)
            item = view.findViewById(R.id.item)

        }

    }

    init {
        inflater = LayoutInflater.from(context)
        var calen = Calendar.getInstance()
        year = calen[Calendar.YEAR]
        month = calen[Calendar.YEAR]+1
        day = calen[Calendar.DAY_OF_MONTH]

    }


    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(p0: Int): BillBean? {
        return dataList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        var view = view
        var holder: ViewHolder? = null
        val three = Typeface.createFromAsset(context.assets, "3.ttf")
        val Sketchy = Typeface.createFromAsset(context.assets, "Sketchy.ttf")

        //initialize the holder
        if (view == null){
            view = inflater.inflate(R.layout.main_item,viewGroup,false)
            holder = ViewHolder(view)
            view.tag = holder
        }
        kotlin.run {
            holder =
                view?.tag as ViewHolder
        }
        val b = dataList[i]
        b?.getSelectedImage()?.let { holder?.typeImage?.setImageResource(it) }
        if (b?.kind==1){
            holder?.item?.setBackgroundResource(R.drawable.item_bg_orange)
        }else if (b?.kind == 0){
            holder?.item?.setBackgroundResource(R.drawable.item_bg_blue)

        }
        holder?.typeName?.text = b?.typeName
        holder?.notes?.text = b?.notes
        holder?.money?.text = "$ " + String.valueOf(b?.money)
        if ((b?.year == year && b?.month == month && b.day == day)) {
            val datetime = b.dateTime?.split(" ")?.toTypedArray()?.get(1)
            holder!!.dateTime.text = "Today $datetime"
            holder!!.dateTime.setTypeface(three)
        }else{
            holder!!.dateTime.text = b?.dateTime
            holder!!.dateTime.setTypeface(three)
        }
        holder!!.notes.setTypeface(three)
        holder!!.typeName.setTypeface(Sketchy)
        holder!!.money.setTypeface(three)


        return view
    }
}