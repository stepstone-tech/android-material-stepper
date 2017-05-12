package com.stepstone.stepper.internal.widget.pagetransformer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewPager;

import com.stepstone.stepper.R;

import static android.support.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Creates a page transformer to be used by {@link com.stepstone.stepper.internal.widget.StepViewPager}.
 *
 * @author Piotr Zawadzki
 */
@RestrictTo(LIBRARY)
public final class StepPageTransformerFactory {

    private StepPageTransformerFactory() {
    }

    /**
     * Creates a {@link android.support.v4.view.ViewPager.PageTransformer}.
     * If layout direction is in RTL it returns {@link StepperRtlPageTransformer}, <i>null</i> otherwise.
     * @param context context
     * @return page transformer
     */
    @Nullable
    public static ViewPager.PageTransformer createPageTransformer(@NonNull Context context) {
        boolean rtlEnabled = context.getResources().getBoolean(R.bool.ms_rtlEnabled);
        return rtlEnabled ? new StepperRtlPageTransformer() : null;
    }

}
