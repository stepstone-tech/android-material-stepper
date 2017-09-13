package com.stepstone.stepper.sample;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Locale;

/**
 * Base test class.
 *
 * @author Piotr Zawadzki
 */
public abstract class AbstractActivityTest<STARTING_ACTIVITY extends Activity> {

    @Rule
    public IntentsTestRule<STARTING_ACTIVITY> intentsTestRule = new IntentsTestRule<>(getStartingActivityClass());

    @NonNull
    protected String getScreenshotTag(int position, @NonNull String title) {
        return String.format(Locale.ENGLISH, "%02d", position) + ". " + title;
    }

    /**
     * Gets the class from the generic type.
     * <a href="http://stackoverflow.com/a/17767068/973379">Source</a>
     *
     * @return the class from the generic type.
     */
    @SuppressWarnings("unchecked")
    private Class<STARTING_ACTIVITY> getStartingActivityClass() {
        return (Class<STARTING_ACTIVITY>)
                getParameterizedType()
                        .getActualTypeArguments()[0];
    }

    private ParameterizedType getParameterizedType() {
        ParameterizedType parameterizedType;
        Class<?> clazz = getClass();
        while (clazz != null) {
            try {
                parameterizedType = getGenericSuperclass(clazz);
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments.length > 0
                        && Activity.class.isAssignableFrom((Class<?>) actualTypeArguments[0])) {
                    return parameterizedType;
                } else {
                    clazz = clazz.getSuperclass();
                }
            } catch (ClassCastException ex) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new IllegalStateException("Activity test must contain a type argument which is the tested Activity class");
    }

    private ParameterizedType getGenericSuperclass(Class<?> aClass) {
        return (ParameterizedType) aClass.getGenericSuperclass();
    }

}
