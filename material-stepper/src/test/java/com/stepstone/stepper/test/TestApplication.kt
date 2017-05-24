package com.stepstone.stepper.test

import android.app.Application

import org.mockito.MockitoAnnotations
import org.robolectric.TestLifecycleApplication

import java.lang.reflect.Method

/**
 * @author Piotr Zawadzki
 */
class TestApplication : Application(), TestLifecycleApplication {

    override fun beforeTest(method: Method) {}

    override fun prepareTest(test: Any) {
        MockitoAnnotations.initMocks(test)//initialize all the mocks that were specified using annotations
    }

    override fun afterTest(method: Method) {}

}