package com.stepstone.stepper.sample.test.matcher;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static org.hamcrest.Matchers.is;

/**
 * Checks the currently selected item position in the {@link ViewPager}.
 *
 * @author Piotr Zawadzki
 */
public class ViewPagerPositionMatcher extends BoundedMatcher<View, ViewPager> {

    @NonNull
    private final Matcher<Integer> positionMatcher;

    private ViewPagerPositionMatcher(Matcher<Integer> positionMatcher) {
        super(ViewPager.class);
        this.positionMatcher = positionMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with position: ");
        positionMatcher.describeTo(description);
    }

    @Override
    protected boolean matchesSafely(ViewPager view) {
        return positionMatcher.matches(view.getCurrentItem());
    }

    /**
     * Returns a matcher that matches {@link ViewPager} based on currently selected view position.
     */
    public static Matcher<View> hasPagePosition(@NonNull final Matcher<Integer> positionMatcher) {
        return new ViewPagerPositionMatcher(checkNotNull(positionMatcher));
    }

    /**
     * Returns a matcher that matches {@link ViewPager} based on currently selected view position.
     */
    public static Matcher<View> hasPagePosition(@IntRange(from = 0) final int position) {
        return new ViewPagerPositionMatcher(is(position));
    }
}
