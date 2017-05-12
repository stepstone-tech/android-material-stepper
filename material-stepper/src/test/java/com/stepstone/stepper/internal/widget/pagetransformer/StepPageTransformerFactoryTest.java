package com.stepstone.stepper.internal.widget.pagetransformer;

import android.os.Build;
import android.support.v4.view.ViewPager;

import com.stepstone.stepper.test.runner.StepperRobolectricTestRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Piotr Zawadzki
 */
@RunWith(StepperRobolectricTestRunner.class)
public class StepPageTransformerFactoryTest {

    @Test
    @Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR1, qualifiers = StepperRobolectricTestRunner.QUALIFIER_LDRTL)
    public void should_create_rtl_page_transformer_on_JB_MR1_when_RTL_locale_selected() {
        //when
        ViewPager.PageTransformer pageTransformer = StepPageTransformerFactory.createPageTransformer(RuntimeEnvironment.application);

        //then
        assertNotNull("PageTransformer cannot be null", pageTransformer);
        assertThat(pageTransformer, is(instanceOf(StepperRtlPageTransformer.class)));
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void should_return_null_by_default_on_JB_MR1() {
        //when
        ViewPager.PageTransformer pageTransformer = StepPageTransformerFactory.createPageTransformer(RuntimeEnvironment.application);

        //then
        assertNull("PageTransformer must be null", pageTransformer);
    }

    @Test
    @Config(sdk = Build.VERSION_CODES.JELLY_BEAN)
    public void should_return_null_by_default_on_JB() {
        //when
        ViewPager.PageTransformer pageTransformer = StepPageTransformerFactory.createPageTransformer(RuntimeEnvironment.application);

        //then
        assertNull("PageTransformer must be null", pageTransformer);
    }

    /**
     * Layout direction qualifiers were added in JB MR1 so below this OS version RTL should be ignored.
     */
    @Test
    @Config(sdk = Build.VERSION_CODES.JELLY_BEAN, qualifiers = StepperRobolectricTestRunner.QUALIFIER_LDRTL)
    public void should_return_null_on_JB_when_RTL_locale_selected() {
        //when
        ViewPager.PageTransformer pageTransformer = StepPageTransformerFactory.createPageTransformer(RuntimeEnvironment.application);

        //then
        assertNull("PageTransformer must be null", pageTransformer);
    }

}