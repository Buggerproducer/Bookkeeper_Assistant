package utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.mybookkeeperassistant.R
import kotlinx.android.synthetic.main.budget.*

class BudgetDialog (context: Context) : Dialog(context) {
    lateinit var cancel: ImageView
    lateinit var done: Button
    lateinit var money: EditText

    interface OnEnsureListener {
        fun onEnsure(money: Float)
    }

    var onEnsureListener: OnEnsureListener? = null
    @JvmName("setOnEnsureListener1")
    fun setOnEnsureListener(onEnsureListener: OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.budget)
        cancel = budget_back
        done = budget_done
        money = budget_input
        done.setOnClickListener{
            val amount = money!!.text.toString()
            if (TextUtils.isEmpty(amount)){
                Toast.makeText(context, "Input cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val total = amount.toFloat()
            if (total <= 0){
                Toast.makeText(context, "The Budget must greater than $0", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (onEnsureListener != null) {
                onEnsureListener!!.onEnsure(total)
            }
            cancel()
        }
        cancel.setOnClickListener{

            cancel()
        }
    }


//reference1 begin
    //set the size of the dialog
    fun setDialogSize() {
        //get window object
        val window = window
        //get the param of the object
        val wlp = window!!.attributes
        //get the width of the screen
        val d = window.windowManager.defaultDisplay
        wlp.width = d.width //dialog width = screen width
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.white)
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
//reference1 end
}