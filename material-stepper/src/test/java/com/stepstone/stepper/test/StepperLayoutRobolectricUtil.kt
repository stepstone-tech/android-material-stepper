package com.stepstone.stepper.test

import android.util.AttributeSet
import com.stepstone.stepper.R
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.StepperLayoutActivity
import com.stepstone.stepper.StepperLayoutWithAdapterActivity
import com.stepstone.stepper.viewmodel.StepViewModel
import org.robolectric.Robolectric
import org.robolectric.android.controller.ActivityController

/**
 * Creates common test operation for creating [StepperLayout] with Robolectric.
 *
 * @author Piotr Zawadzki
 */

val TYPE_PROGRESS_BAR = "progress_bar"
val TYPE_DOTS = "dots"
val TYPE_TABS = "tabs"
val TYPE_NONE = "none"

fun createAttributeSetWithStepperType(stepperType: String): AttributeSet {
    return Robolectric.buildAttributeSet()
            .addAttribute(R.attr.ms_stepperType, stepperType)
            .build()
}

fun createStepperLayoutInActivity(attributeSet: AttributeSet): StepperLayout {
    val activity = ActivityController.of(Robolectric.getShadowsAdapter(), StepperLayoutActivity().withStepperLayoutAttributes(attributeSet))
            .setup()
            .get()
    return activity.stepperLayout
}

fun createStepperLayoutWithAdapterSetInActivity(attributeSet: AttributeSet): StepperLayout {
    return createStepperLayoutWithAdapterSetInActivity(attributeSet, null, null, null)
}

/**
 * Creates a [StepperLayout] set in the Activity with [StepViewModel]s provided in the attributes.
 * If a [StepViewModel] is null then the default [StepViewModel] from [StepperLayoutWithAdapterActivity.defaultStepViewModel] is used.
 */
fun createStepperLayoutWithAdapterSetInActivity(attributeSet: AttributeSet,
                                                firstViewModel: StepViewModel?,
                                                middleViewModel: StepViewModel?,
                                                lastViewModel: StepViewModel?): StepperLayout {
    val stepperLayoutWithAdapterActivity = object : StepperLayoutWithAdapterActivity() {

        override fun getFirstStepViewModel() = firstViewModel ?: super.getFirstStepViewModel()

        override fun getSecondStepViewModel() = middleViewModel ?: super.getSecondStepViewModel()

        override fun getLastStepViewModel() = lastViewModel ?: super.getLastStepViewModel()

    }
    val activity = ActivityController.of(Robolectric.getShadowsAdapter(), stepperLayoutWithAdapterActivity.withStepperLayoutAttributes(attributeSet))
            .setup()
            .get()
    return activity.stepperLayout
}