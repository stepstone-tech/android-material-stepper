package com.stepstone.stepper.test;

import android.app.Application;

import org.mockito.MockitoAnnotations;
import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

/**
 * @author Piotr Zawadzki
 */
public class TestApplication extends Application implements TestLifecycleApplication {

        @Override
        public void beforeTest(Method method) {
        }

        @Override
        public void prepareTest(Object test) {
            MockitoAnnotations.initMocks(test);//initialize all the mocks that were specified using annotations
        }

        @Override
        public void afterTest(Method method) {
        }

}