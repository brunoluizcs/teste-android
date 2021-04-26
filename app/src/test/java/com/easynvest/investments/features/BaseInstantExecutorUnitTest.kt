package com.easynvest.investments.features

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.easynvest.investments.utils.MainCoroutineScopeRule
import org.junit.Rule

open class BaseInstantExecutorUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()
}
