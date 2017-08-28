package com.stepstone.stepper.sample.test.spoon;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;

import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.graphics.Bitmap.Config.ARGB_8888;
import static com.stepstone.stepper.sample.test.spoon.Chmod.chmodPlusR;
import static com.stepstone.stepper.sample.test.spoon.Chmod.chmodPlusRWX;

/**
 * Copied from <a href="https://github.com/square/spoon/blob/parent-1.2.0/spoon-client/src/main/java/com/squareup/spoon/Spoon.java">Github</a>.
 * Changed the path where to save the screenshots.
 * <p>
 * Utility class for capturing screenshots for Spoon.
 */
public final class Spoon {

    @SuppressLint("SdCardPath")
    private static String REPORTS_DIRECTORY = "/sdcard/material-stepper-screenshots";

    private static final String SPOON_SCREENSHOTS = "spoon-screenshots";
    private static final String SPOON_FILES = "spoon-files";
    private static final String TEST_CASE_CLASS_JUNIT_3 = "android.test.InstrumentationTestCase";
    private static final String TEST_CASE_METHOD_JUNIT_3 = "runMethod";
    private static final String TEST_CASE_CLASS_JUNIT_4 = "org.junit.runners.model.FrameworkMethod$1";
    private static final String TEST_CASE_METHOD_JUNIT_4 = "runReflectiveCall";
    private static final String EXTENSION = ".png";
    private static final String TAG = "Spoon";

    /**
     * Take a screenshot with the specified tag.  This version allows the caller to manually specify
     * the test class name and method name.  This is necessary when the screenshot is not called in
     * the traditional manner.
     *
     * @param activity Activity with which to capture a screenshot.
     * @param tag      Unique tag to further identify the screenshot. Must match [a-zA-Z0-9_-]+.
     * @return the image file that was created
     */
    public static File screenshot(Activity activity, String tag, String testClassName) {
        try {
            File screenshotDirectory =
                    obtainScreenshotDirectory(testClassName);
            String screenshotName = tag + EXTENSION;
            File screenshotFile = new File(screenshotDirectory, screenshotName);
            takeScreenshot(screenshotFile, activity);
            Log.d(TAG, "Captured screenshot '" + tag + "'.");
            return screenshotFile;
        } catch (Exception e) {
            Log.d(TAG, "Unable to capture screenshot.", e);
            return null;
            //throw new RuntimeException("Unable to capture screenshot.", e);
        }
    }

    private static void takeScreenshot(File file, final Activity activity) throws IOException {
        View view = activity.getWindow().getDecorView();
        final Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), ARGB_8888);

        if (Looper.myLooper() == Looper.getMainLooper()) {
            // On main thread already, Just Do It™.
            drawDecorViewToBitmap(activity, bitmap);
        } else {
            // On a background thread, post to main.
            final CountDownLatch latch = new CountDownLatch(1);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        drawDecorViewToBitmap(activity, bitmap);
                    } finally {
                        latch.countDown();
                    }
                }
            });
            try {
                latch.await();
            } catch (InterruptedException e) {
                String msg = "Unable to get screenshot " + file.getAbsolutePath();
                Log.e(TAG, msg, e);
                throw new RuntimeException(msg, e);
            }
        }

        OutputStream fos = null;
        try {
            fos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(PNG, 100 /* quality */, fos);

            chmodPlusR(file);
        } finally {
            bitmap.recycle();
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static void drawDecorViewToBitmap(Activity activity, Bitmap bitmap) {
        Canvas canvas = new Canvas(bitmap);
        activity.getWindow().getDecorView().draw(canvas);
    }

    private static File obtainScreenshotDirectory(String testClassName) throws IllegalAccessException {
        return filesDirectory(SPOON_SCREENSHOTS, testClassName);
    }

    /**
     * Alternative to {@link #save(Context, File)}
     *
     * @param context Context used to access files.
     * @param path    Path to the file you want to save.
     * @return the copy that was created.
     */
    public static File save(final Context context, final String path) {
        return save(context, new File(path));
    }

    /**
     * Save a file from this test run. The file will be saved under the current class & method.
     * The file will be copied to, so make sure all the data you want have been
     * written to the file before calling save.
     *
     * @param context Context used to access files.
     * @param file    The file to save.
     * @return the copy that was created.
     */
    public static File save(final Context context, final File file) {
        StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
        String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
        String methodName = testClass.getMethodName();
        return save(className, methodName, file);
    }

    private static File save(String className, String methodName, File file) {
        File filesDirectory = null;
        try {
            filesDirectory = filesDirectory(SPOON_FILES, className);
            if (!file.exists()) {
                throw new RuntimeException("Can't find any file at: " + file);
            }

            File target = new File(filesDirectory, file.getName());
            copy(file, target);
            Log.d(TAG, "Saved " + file);
            return target;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(("Unable to save file: " + file));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't copy file " + file + " to " + filesDirectory);
        }
    }

    private static void copy(File source, File target) throws IOException {
        Log.d(TAG, "Will copy " + source + " to " + target);

        target.createNewFile();
        chmodPlusR(target);

        final BufferedInputStream is = new BufferedInputStream(new FileInputStream(source));
        final BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(target));
        byte[] buffer = new byte[4096];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }
        is.close();
        os.close();
    }

    private static File filesDirectory(String directoryType, String testClassName) throws IllegalAccessException {
        File directory;
        directory = new File(REPORTS_DIRECTORY, "app_" + directoryType);

        File dirClass = new File(directory, testClassName);
        createDir(dirClass);
        return dirClass;
    }

    /**
     * Returns the test class element by looking at the method InstrumentationTestCase invokes.
     */
    static StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
        for (int i = trace.length - 1; i >= 0; i--) {
            StackTraceElement element = trace[i];

            if (TEST_CASE_CLASS_JUNIT_3.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_JUNIT_3.equals(element.getMethodName())) {
                return trace[i - 3];
            }

            if (TEST_CASE_CLASS_JUNIT_4.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_JUNIT_4.equals(element.getMethodName())) {
                return trace[i - 3];
            }
        }

        throw new IllegalArgumentException("Could not find test class!");
    }

    private static void createDir(File dir) throws IllegalAccessException {
        File parent = dir.getParentFile();
        if (!parent.exists()) {
            createDir(parent);
        }
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalAccessException("Unable to create output dir: " + dir.getAbsolutePath());
        }
        chmodPlusRWX(dir);
    }

    private Spoon() {
        // No instances.
    }
}
