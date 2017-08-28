package com.stepstone.stepper.sample.test.action;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.util.Log;
import android.view.View;

import com.stepstone.stepper.sample.test.spoon.Spoon;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.io.File;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Class to take screenshots using Spoon library from an Espresso test
 * Original code from Gist: https://gist.github.com/edenman/7fdd32a4d59ccc01185b
 */
public final class SpoonScreenshotAction implements ViewAction {

    private static final String TAG = SpoonScreenshotAction.class.getSimpleName();

    private static File lastScreenshot;

    private final String mTag;
    private final String mTestClass;

    /**
     * Get the last captured screenshot file
     *
     * @return Last screenshot file handler or null if there was no screenshot taken
     */
    public static File getLastScreenshot() {
        return lastScreenshot;
    }

    /**
     * Initialize with information required to take a screenshot
     *
     * @param tag        Name of the screenshot to include in the file name
     * @param testClass  Name of the class taking the screenshot (required by Spoon library)
     */
    private SpoonScreenshotAction(final String tag, final String testClass) {
        mTag = tag;
        mTestClass = testClass;
    }

    @Override
    public Matcher<View> getConstraints() {
        return Matchers.any(View.class);
    }

    @Override
    public String getDescription() {
        return "Taking a screenshot using spoon.";
    }

    @Override
    public void perform(final UiController uiController, final View view) {
        lastScreenshot = Spoon.screenshot(getActivity(view), mTag, mTestClass);
        Log.d(TAG, "Screenshot recorded at: " + (lastScreenshot != null ? lastScreenshot.getPath() : "NOT RECORDED"));
    }

    /**
     * Get the activity from the context of the view
     *
     * @param view View from which the activity will be inferred
     * @return Activity that contains the given view
     */
    private static Activity getActivity(final View view) {
        Context context = view.getContext();
        while (!(context instanceof Activity)) {
            if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                throw new IllegalStateException("Got a context of class " + context.getClass() + " and I don't know "
                        + "how to get the Activity from it");
            }
        }
        return (Activity) context;
    }

    /**
     * Espresso action to be take a screenshot of the current activity
     * This must be called directly from the test method
     *
     * @param tag Name of the screenshot to include in the file name
     */
    public static void perform(final String tag) {
        final StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        final String testClass = trace[3].getClassName();
        onView(withId(android.R.id.content)).perform(new SpoonScreenshotAction(tag, testClass));
    }
}
