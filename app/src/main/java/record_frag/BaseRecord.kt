package record_frag

import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mybookkeeperassistant.R
import database.BillBean
import database.TypeBean
import kotlinx.android.synthetic.main.base_fragment.*
import utils.KeyBoardUtils
import utils.NoteDialog
import utils.TimeDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseRecord.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseRecord(val userID:Int) : Fragment(),View.OnClickListener {
    // TODO: Rename and change types of parameters
    lateinit var keyboard:KeyboardView
    lateinit var money: EditText
    lateinit var typeImage:ImageView
    lateinit var typeName:TextView
    lateinit var note:TextView
    lateinit var time:TextView
    lateinit var typeGrid: GridView
    lateinit var typeList: List<TypeBean>
    lateinit var baseAdapter: TypeBaseAdapter
    lateinit var billBean: BillBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        billBean = BillBean()
        billBean.typeName = "Others"
        billBean.setSelectedImage(R.mipmap.other_activate)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.base_fragment, container, false)
        initializeView(view)
        setCurrentTime()
        loadTypeGrid()
        setGridListener()
        return view
    }

    private fun setGridListener() {
        typeGrid.onItemClickListener =
            AdapterView.OnItemClickListener() { adapterView, view, i, l ->
                baseAdapter.selectPos = i
                baseAdapter.notifyDataSetInvalidated()//remind change draw
                val typeBean = typeList.get(i)
                val new_typeName = typeBean.typeName
                typeName.setText(new_typeName)
                billBean.typeName = new_typeName

                val new_selectedImageId = typeBean.selectedImageId
                typeImage.setImageResource(new_selectedImageId)
                billBean.setSelectedImage(new_selectedImageId)

            }
    }

    // put data into grid view
    open fun loadTypeGrid() {
        typeList = ArrayList<TypeBean>()
        baseAdapter = context?.let { TypeBaseAdapter(it,typeList) }!!
        typeGrid.adapter = baseAdapter
    }

    //set click event on each item in gridview
    private fun setCurrentTime() {
        val date = Date()
        val d = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val dateTime = d.format(date)
        time.text = dateTime
        billBean.dateTime = dateTime

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        billBean.year = year
        billBean.month = month
        billBean.day = day


    }

    private fun initializeView(view: View?) {
        if (view != null){
            keyboard =  view.findViewById(R.id.fragment_keyboard)
            money = view.findViewById(R.id.fragment_money_input)
            typeName = view.findViewById(R.id.fragment_header_typeName)
            typeImage = view.findViewById(R.id.fragment_header_typeImage)
            note = view.findViewById(R.id.fragment_notes)
            time = view.findViewById(R.id.fragment_dateTime)
            typeGrid = view.findViewById(R.id.fragment_typeList)
        }

        note.setOnClickListener{
            showNoteDialog()
        }
        time.setOnClickListener {
            showTimeDialog()
        }

        var keyboardUtils = KeyBoardUtils(keyboard,money)
        keyboardUtils.showKeyboard();
        keyboardUtils.onEnsureListener = object : KeyBoardUtils.OnEnsureListener{
            override fun onEnsure() {
                //get the input amount
                val money_str = money.text.toString()
                if (TextUtils.isEmpty(money_str) || money_str.toFloat() == 0.0f){
                    activity?.finish()
                    return
                }
                val m = money_str.toFloat()
                //key the money
                billBean.money = m
                saveAccountToDB()
                activity?.finish()
            }
        }
    }

    private fun showNoteDialog() {
        val dialog = context?.let { NoteDialog(it) }
        dialog?.setSize();//set the dialog size

        if (dialog != null) {
            dialog.onEnsureListener = object : NoteDialog.OnEnsureListener {
                override fun onEnusre() {
                    val msg = dialog.editText
                    if (!TextUtils.isEmpty(msg)) {
                        note.setText(msg)
                        billBean.notes = msg
                    }
                }
            }
        }
        dialog?.show();
    }

    private fun showTimeDialog() {
        val dialog = context?.let { TimeDialog(it) }

        if (dialog != null) {
            dialog.onEnsureListener = object : TimeDialog.OnEnsureListener {
                override fun onEnsure(t: String?, year: Int, month: Int, day: Int) {
                    time.setText(t)
                    billBean.dateTime = t
                    billBean.year = year
                    billBean.month = month
                    billBean.day = day
                }
            }
        }
        dialog?.show()
    }

    //the children class must override this
    open fun saveAccountToDB() {

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BaseRecord.
         */
        // TODO: Rename and change types and number of parameters

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}