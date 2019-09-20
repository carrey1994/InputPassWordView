package com.example.passwordinputproject

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.support.v7.widget.AppCompatEditText
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class SecretInputView : AppCompatEditText {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        setupTypeArray(attrs!!)
        setupPaints()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupTypeArray(attrs!!)
        setupPaints()
    }

    private lateinit var paintRect: Paint
    private lateinit var paintDot: Paint
    private var digitsLength: Int = 0
    private var borderColor: Int = 0
    private var dotColor: Int = 0
    private var rectWidth: Float = 0F
    private var gapWidth: Float = 0F
    private var startGap: Float = 0F


    init {
        isFocusable = true
        isFocusableInTouchMode = true
        inputType = InputType.TYPE_CLASS_NUMBER
        background = null
        isCursorVisible = false
    }

    private fun setupPaints() {

        //prepare paint for drawing the rect
        paintRect = Paint(Paint.ANTI_ALIAS_FLAG)
        paintRect.style = Paint.Style.STROKE
        paintRect.strokeWidth = 3F
        paintRect.color = borderColor

        //prepare paint for drawing the dot
        paintDot = Paint(Paint.ANTI_ALIAS_FLAG)
        paintDot.strokeWidth = 15F
        paintDot.color = dotColor
        paintDot.strokeCap = Paint.Cap.ROUND

        //limit the length of input text
        filters = arrayOf(InputFilter.LengthFilter(digitsLength))
    }

    @SuppressLint("Recycle")
    private fun setupTypeArray(attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.SecretInputView)
        borderColor = typeArray.getColor(R.styleable.SecretInputView_border_color, Color.parseColor("#516994"))
        dotColor = typeArray.getColor(R.styleable.SecretInputView_dot_color, Color.parseColor("#516994"))
        digitsLength = typeArray.getInt(R.styleable.SecretInputView_digit_length, 3)
        rectWidth = typeArray.getDimension(R.styleable.SecretInputView_rect_width, 30F)
        gapWidth = typeArray.getDimension(R.styleable.SecretInputView_gap_width, 10F)
        startGap = typeArray.getDimension(R.styleable.SecretInputView_start_gap, 10F)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        //Draw the rect for input
        for (i in 0 until digitsLength) {
            val rect =
                RectF(
                    startGap + i * (rectWidth + gapWidth),
                    3F,
                    startGap + rectWidth + i * (rectWidth + gapWidth),
                    rectWidth
                )
            canvas?.drawRoundRect(rect, 10F, 10F, paintRect)
        }

        //Draw the dot on typing
        if (!text.isNullOrBlank()) {
            for (i in 0 until text!!.length) {
                canvas?.drawPoint(startGap + rectWidth / 2F + i * (rectWidth + gapWidth), rectWidth / 2F, paintDot)
            }
        }


    }

}