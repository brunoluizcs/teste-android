package com.easynvest.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_investment_item.view.*

class InvestmentItemComponent : ConstraintLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val view: View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        view = LayoutInflater.from(context)
            .inflate(R.layout.component_investment_item, this, true)

        initializeAttributes(context, attrs)
    }

    var text1: String? = null
        set(value) {
            view.text1.text = value
            field = value
        }

    var text2: String? = null
        set(value) {
            view.text2.text = value
            field = value
        }

    fun setTexts(text1: String, text2: String) {
        this.text1 = text1
        this.text2 = text2
    }

    private fun initializeAttributes(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InvestmentItemComponent,
            0, 0
        ).apply {
            try {
                text1 = getString(R.styleable.InvestmentItemComponent_text1)
                text2 = getString(R.styleable.InvestmentItemComponent_text2)
            } finally {
                recycle()
            }
        }
    }
}
