package com.stepstone.stepper.test.runner

import android.os.Build

import com.stepstone.stepper.BuildConfig
import com.stepstone.stepper.test.TestApplication

import org.junit.runners.model.InitializationError
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

import java.lang.reflect.Method

/**
 * Test runner with default config for this library.
 * Inspired by [Robolectric Bulk Test Configuration](https://medium.com/@andrewlord1990/robolectric-bulk-test-configuration-28ddf82abf4a).
 */
class StepperRobolectricTestRunner @Throws(InitializationError::class) constructor(testClass: Class<*>) : RobolectricTestRunner(testClass) {

    companion object {

        const val QUALIFIER_LDRTL = "ldrtl"

    }

    override fun getConfig(method: Method): Config {
        val config = super.getConfig(method)
        return Config.Builder(config)
                .setSdk(*resolveSdk(config.sdk))
                .setConstants(resolveBuildConfig(config.constants.java))
                .setApplication(TestApplication::class.java)
                .build()
    }

    private fun resolveSdk(sdks: IntArray?): IntArray {
        if (sdks == null || sdks.isEmpty()) {
            return intArrayOf(Build.VERSION_CODES.LOLLIPOP)
        } else {
            return sdks
        }
    }

    private fun resolveBuildConfig(constants: Class<*>): Class<*> {
        if (constants == Void::class.java) {
            return BuildConfig::class.java
        } else {
            return constants
        }
    }

}
