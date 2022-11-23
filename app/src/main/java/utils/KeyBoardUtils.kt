package utils

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.example.mybookkeeperassistant.R
//reference 2
class KeyBoardUtils(private val keyboardView: KeyboardView, private val editText: EditText) {
    private val k1: Keyboard

    interface OnEnsureListener {
        fun onEnsure()
    }

    var onEnsureListener: OnEnsureListener? = null
    @JvmName("setOnEnsureListener1")
    fun setOnEnsureListener(onEnsureListener: OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }

    var listener: KeyboardView.OnKeyboardActionListener = object :
        KeyboardView.OnKeyboardActionListener {
        override fun onPress(i: Int) {}
        override fun onRelease(i: Int) {}
        override fun onKey(i: Int, ints: IntArray) {
            val editable = editText.text
            val start = editText.selectionStart
            when (i) {
                Keyboard.KEYCODE_DELETE -> if (editable != null && editable.length > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start)
                    }
                }
                Keyboard.KEYCODE_CANCEL -> editable!!.clear()
                Keyboard.KEYCODE_DONE -> onEnsureListener!!.onEnsure() //using the interface callback, when click the done, can use this method
                else -> editable!!.insert(start, Character.toString(i.toChar()))
            }
        }

        override fun onText(charSequence: CharSequence) {}
        override fun swipeLeft() {}
        override fun swipeRight() {}
        override fun swipeDown() {}
        override fun swipeUp() {}
    }

    //the method used to display the keyboard
    fun showKeyboard() {
        val visiblity = keyboardView.visibility
        if (visiblity == View.INVISIBLE || visiblity == View.GONE) {
            keyboardView.visibility = View.VISIBLE
        }
    }

    //the method is used to hid the keybord
    fun hideKeyboard() {
        val visibility = keyboardView.visibility
        if (visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            keyboardView.visibility = View.GONE
        }
    }

    init {
        editText.inputType = InputType.TYPE_NULL //cancel the default keyboard
        k1 = Keyboard(editText.context, R.xml.key)
        keyboardView.keyboard = k1 //set the style of the keyboard to be shown
        keyboardView.isEnabled = true
        keyboardView.isPreviewEnabled = false
        keyboardView.setOnKeyboardActionListener(listener) //set the listener of the keys that are pressed
    }
}