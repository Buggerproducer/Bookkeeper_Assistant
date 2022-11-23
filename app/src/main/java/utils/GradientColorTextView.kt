package utils

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.widget.TextView

//this class is used to set the gradient color of the text
//reference 3P
class GradientColorTextView(context: Context?, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context!!, attrs) {
    private lateinit var mLinearGradient: LinearGradient
    private var mPaint: Paint? = null
    private var mViewWidth = 0
    private val mTextBound = Rect()
    override fun onDraw(canvas: Canvas) {
        mViewWidth = measuredWidth
        mPaint = paint
        val mTipText = text.toString()
        (mPaint as TextPaint).getTextBounds(mTipText, 0, mTipText.length, mTextBound)
        mLinearGradient = LinearGradient(
            0f, 0f, mViewWidth.toFloat(), 0f, intArrayOf(-0x1546, -0x4174b7),
            null, Shader.TileMode.REPEAT
        )
        (mPaint as TextPaint).setShader(mLinearGradient)
        canvas.drawText(
            mTipText,
            (measuredWidth / 2 - mTextBound.width() / 2).toFloat(),
            (measuredHeight / 2 + mTextBound.height() / 2).toFloat(), mPaint!!
        )
    }
}