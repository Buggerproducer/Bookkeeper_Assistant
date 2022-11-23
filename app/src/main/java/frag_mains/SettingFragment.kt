package frag_mains

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.mybookkeeperassistant.R
import database.ManageDB
import kotlinx.android.synthetic.main.activity_setting.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment(val t:FragmentActivity) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        val mview = inflater.inflate(R.layout.activity_setting, container, false)
        val back: ImageView = mview.findViewById(R.id.setting_back)
        back.setOnClickListener {
            t.finish()
        }
        val delete_all: TextView = mview.findViewById(R.id.setting_delete_all)
        delete_all.setOnClickListener {
            showWarning()
        }
        return mview
    }

    private fun showWarning() {
        val builder = AlertDialog.Builder(t)
        builder.setTitle("Warning").setMessage("Are you sure to delete ALL RECORDS!")
            .setNegativeButton("Cancel", null)
            .setPositiveButton(
                "Sure"
            ) { dialogInterface, i ->
                ManageDB.deleteAll()
                Toast.makeText(t, "Delete Successfully", Toast.LENGTH_SHORT).show()

            }
        builder.create().show() //make the dialog visible

}

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
    }
}