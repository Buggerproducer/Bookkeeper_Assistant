package utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.example.mybookkeeperassistant.R
import kotlinx.android.synthetic.main.change_name.*
import kotlinx.android.synthetic.main.notes.*

class ChangeNameDialog (context: Context): Dialog(context){
    lateinit var input: EditText
    lateinit var doneBtn: Button
    lateinit var cancelBtn: Button
    var onEnsureListener: OnEnsureListener? = null

    interface OnEnsureListener {
        fun onEnusre()
    }

    //callback interface
    @JvmName("setOnEnsureListener1")
    fun setOnEnsureListener(onEnsureListener: OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_name)
        input = change_name_input
        doneBtn = change_name_done
        cancelBtn = change_name_cancel
        cancelBtn.setOnClickListener{
            cancel()
        }
        doneBtn.setOnClickListener{
            if (onEnsureListener != null){
                onEnsureListener!!.onEnusre()
                cancel()
            }
        }
    }

    //leave out the white space

    val editText: String
        get() = input!!.text.toString().trim { it <= ' ' } //leave out the white space


    //reference1 begin
    //set the size of the dialog
    fun setSize() {
        //get window object
        val window = window
        //get the param of the object
        val wlp = window!!.attributes
        //get the width of the screen
        val default = window.windowManager.defaultDisplay
        wlp.width = default.width //dialog width = screen width
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
        handler.sendEmptyMessageDelayed(1, 100) //delay 0.1s to present the dialog
    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            //automatically present the keyboard
            val inputMethodManager =
                getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    //reference end
}