package ru.fefu.lesson_5.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import ru.fefu.lesson_5.R
import kotlin.math.max
import kotlin.math.min

class QuantityControlView : LinearLayoutCompat {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    private val tvQuantity by lazy { findViewById<TextView>(R.id.tvQuantity) }
    private val btnIncrease by lazy { findViewById<ImageButton>(R.id.btnIncrease) }
    private val btnDecrease by lazy { findViewById<ImageButton>(R.id.btnDecrease) }

    private var quantityChangeListener: QuantityChangeListener? = null

    private var quantity: Int = 0
        set(value) {
            field = max(0, min(value, 99))
            tvQuantity.text = quantity.toString()
            quantityChangeListener?.onQuantityChanged(field)
        }

    fun setQuantityChangeListener(listener: QuantityChangeListener?) {
        this.quantityChangeListener = listener
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_quantity_control, this, true)
        btnIncrease.setOnClickListener { quantity++ }
        btnDecrease.setOnClickListener { quantity-- }
        tvQuantity.text = quantity.toString()
    }

    interface QuantityChangeListener {
        fun onQuantityChanged(quantity: Int)
    }

}