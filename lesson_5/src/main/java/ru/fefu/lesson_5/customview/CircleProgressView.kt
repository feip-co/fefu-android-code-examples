package ru.fefu.lesson_5.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import ru.fefu.lesson_5.R
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min

class CircleProgressView : View {

    private val progressPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = progressWidth.toFloat()
        isAntiAlias = true
    }

    private val oval = RectF()

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent?): Boolean {
            e?.let { updateProgress(it.x, it.y) }
            Log.d("CircleProgress", "onDown")
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            e2?.let { updateProgress(it.x, it.y) }
            Log.d("CircleProgress", "onScroll")
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d("CircleProgress", "onFling")
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    private val gestureDetector by lazy { GestureDetector(context, gestureListener) }

    private var progressColor: Int = Color.RED
    private var secondaryProgressColor: Int = Color.BLUE
    private var progressWidth: Int = 10
    private var maxValue: Int = 100

    private var verticalPadding = 0f
    private var horizontalPadding = 0f
    private var radius = 0f

    private var centerX = 0f
    private var centerY = 0f

    private var currentProgress = 0
        set(value) {
            field = if (value > maxValue) 0 else value
            invalidate()
        }

    constructor(context: Context) : super(context) {
        initAttr(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttr(attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val wrapSize = progressWidth * 6

        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val width =  when (widthMode) { /*resolveSize(wrapSize, widthMeasureSpec)*/
            MeasureSpec.EXACTLY -> measuredWidth
            MeasureSpec.AT_MOST -> min(wrapSize, measuredWidth)
            else -> wrapSize
        }
        val height = when (heightMode) { /*resolveSize(wrapSize, heightMeasureSpec)*/
            MeasureSpec.EXACTLY -> measuredHeight
            MeasureSpec.AT_MOST -> min(wrapSize, measuredHeight)
            else -> wrapSize
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        //draw circle
        progressPaint.color = secondaryProgressColor
        canvas.drawCircle(centerX, centerY, radius, progressPaint)

        //draw progress circle
        val progressAngle = currentProgress / maxValue.toFloat() * 360
        oval.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
        progressPaint.color = progressColor
        canvas.drawArc(oval, -90f, progressAngle, false, progressPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = width / 2f
        centerY = height / 2f

        verticalPadding = when {
            height > width -> 0f
            else -> progressWidth / 2f
        }

        horizontalPadding = when {
            width > height -> 0f
            else -> progressWidth / 2f
        }

        radius = min(height, width) / 2f - max(horizontalPadding, verticalPadding)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun updateProgress(x: Float, y: Float) {
        val relativeX = x - centerX
        val relativeY = y - centerY
        val degrees = (atan2(relativeY.toDouble(), relativeX.toDouble()) + 90f) * 180 / Math.PI % 360f

        currentProgress = (degrees / 360 * 100).toInt()
        Log.d("CircleProgress", "Angle: $degrees")
    }

    private fun initAttr(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)
        progressColor = typedArray.getColor(
            R.styleable.CircleProgressView_cpv_ProgressColor,
            progressColor
        )
        secondaryProgressColor = typedArray.getColor(
            R.styleable.CircleProgressView_cpv_SecondaryProgressColor,
            secondaryProgressColor
        )
        maxValue = typedArray.getInteger(
            R.styleable.CircleProgressView_cpv_MaxValue,
            maxValue
        )
        progressWidth = typedArray.getDimensionPixelSize(
            R.styleable.CircleProgressView_cpv_ProgressWidth,
            progressWidth
        )

        progressPaint.strokeWidth = progressWidth.toFloat()
        typedArray.recycle()
    }

}