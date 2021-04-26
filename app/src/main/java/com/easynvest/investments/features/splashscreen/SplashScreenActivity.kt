package com.easynvest.investments.features.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.easynvest.investments.R
import com.easynvest.investments.databinding.ActivitySplashScreenBinding
import com.easynvest.investments.features.form.FormActivity
import com.easynvest.views.extensions.TransitionProperty
import com.easynvest.views.extensions.doBounceAnimation

class SplashScreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler()

    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }

    companion object {
        const val START_ANIMATION_POSITION = -1000f
        const val END_ANIMATION_POSITION = 0f
    }

    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        binding.fullscreenContent.doBounceAnimation(
            property = TransitionProperty.TRANSLATIONY,
            onStart = this::onAnimationStarted,
            onEnd = this::onAnimationEnded,
            values = floatArrayOf(START_ANIMATION_POSITION, END_ANIMATION_POSITION)
        )

        mVisible = true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        delayedHide(100)
    }

    private fun hide() {
        supportActionBar?.hide()
        mVisible = false
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    private fun onAnimationStarted() {
        binding.fullscreenContent.visibility = View.VISIBLE
    }

    private fun onAnimationEnded() {
        navigateToNextScreen()
    }

    private fun navigateToNextScreen() {
        startActivity(Intent(this, FormActivity::class.java))
        finish()
    }
}
