package com.easynvest.views.extensions

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import com.daasuu.ei.Ease
import com.daasuu.ei.EasingInterpolator

fun View.visible(visible: Boolean = false) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.doBounceAnimation(
    property: TransitionProperty,
    startDelayInMls: Long = 150,
    durationInMls: Long = 1500,
    onStart: (() -> Unit)? = null,
    onEnd: (() -> Unit?)? = null,
    vararg values: Float
) {
    val animator = ObjectAnimator.ofFloat(this, property.value, *values)
    animator.interpolator = EasingInterpolator(Ease.BACK_OUT)
    animator.startDelay = startDelayInMls
    animator.duration = durationInMls
    animator.doOnStart { onStart?.invoke() }
    animator.doOnEnd { onEnd?.invoke() }
    animator.start()
}

enum class TransitionProperty(val value: String) {
    TRANSLATIONY("translationY"),
    TRANSLATIONX("translationX")
}
