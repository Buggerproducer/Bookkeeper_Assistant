package record_frag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.example.mybookkeeperassistant.R
import database.TypeBean

class TypeBaseAdapter(var context: Context?, dataList: List<TypeBean>) :
    BaseAdapter() {
    var selectPos = 0
    var dataList: List<TypeBean>
    var typeList: GridView? = null
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(i: Int): Any {
        return dataList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    //repeat use is not accounted for this part
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        view = LayoutInflater.from(context).inflate(R.layout.type_list_item, viewGroup, false)
        //find the controller from layout
        val image = view.findViewById<ImageView>(R.id.list_item_image)
        val text = view.findViewById<TextView>(R.id.list_item_text)
        val typeBean: TypeBean = dataList[i]
        text.setText(typeBean.typeName)
        //judge if the current posistion is selected,if yes, image filled with color
        if (selectPos == i) {
            image.setImageResource(typeBean.selectedImageId)
        } else {
            image.setImageResource(typeBean.imageId)
        }
        return view
    }

    private fun setGVListener() {
        typeList!!.onItemClickListener =
            OnItemClickListener { adapterView, view, i, l -> }
    }

    init {
        this.dataList = dataList
    }
}
