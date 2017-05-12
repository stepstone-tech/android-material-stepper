package com.stepstone.stepper.test.runner;

import android.os.Build;

import com.stepstone.stepper.BuildConfig;
import com.stepstone.stepper.test.TestApplication;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Method;

/**
 * Test runner with default config for this library.
 * Inspired by <a href="https://medium.com/@andrewlord1990/robolectric-bulk-test-configuration-28ddf82abf4a">Robolectric Bulk Test Configuration</a>.
 */
public class StepperRobolectricTestRunner extends RobolectricTestRunner {

    public static final String QUALIFIER_LDRTL = "ldrtl";

    public StepperRobolectricTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    @Override
    public Config getConfig(Method method) {
        Config config = super.getConfig(method);
        return new Config.Builder(config)
                .setSdk(getSdk(config.sdk()))
                .setConstants(getBuildConfig(config.constants()))
                .setApplication(TestApplication.class)
                .build();
    }

    private int[] getSdk(int[] sdks) {
        if (sdks == null || sdks.length == 0) {
            return new int[]{Build.VERSION_CODES.LOLLIPOP};
        } else {
            return sdks;
        }
    }

    private Class<?> getBuildConfig(Class<?> constants) {
        if (constants == Void.class) {
            return BuildConfig.class;
        } else {
            return constants;
        }
    }

}
