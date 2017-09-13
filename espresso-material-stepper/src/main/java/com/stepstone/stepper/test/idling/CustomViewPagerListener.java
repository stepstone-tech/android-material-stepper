package com.stepstone.stepper.test.idling;


import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v4.view.ViewPager;

/**
 * View pager listener that serves as Espresso's {@link IdlingResource} and notifies the
 * registered callback when the view pager gets to STATE_IDLE state.
 * <p>
 * A copy of {@code android.support.test.espresso.contrib.ViewPagerActions.CustomViewPagerListener}.
 */
public final class CustomViewPagerListener
        implements ViewPager.OnPageChangeListener, IdlingResource {

    private int mCurrState = ViewPager.SCROLL_STATE_IDLE;

    @Nullable
    private IdlingResource.ResourceCallback mCallback;

    public boolean mNeedsIdle = false;

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        mCallback = resourceCallback;
    }

    @Override
    public String getName() {
        return "View pager listener";
    }

    @Override
    public boolean isIdleNow() {
        if (!mNeedsIdle) {
            return true;
        } else {
            return mCurrState == ViewPager.SCROLL_STATE_IDLE;
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mCurrState == ViewPager.SCROLL_STATE_IDLE) {
            if (mCallback != null) {
                mCallback.onTransitionToIdle();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mCurrState = state;
        if (mCurrState == ViewPager.SCROLL_STATE_IDLE) {
            if (mCallback != null) {
                mCallback.onTransitionToIdle();
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
}
