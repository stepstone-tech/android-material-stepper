package com.stepstone.stepper.internal.feedback;


import android.support.annotation.NonNull;


/**
 * An interface to be implemented by all support stepper feedback types.
 * It contains methods which allow to show feedback for the duration of some executed operation.
 *
 * @author Piotr Zawadzki
 */
public interface StepperFeedbackType {

    /**
     * No changes during operation.
     */
    int NONE = 1;

    /**
     * Show a progress message instead of the tabs during operation.
     * @see TabsStepperFeedbackType
     */
    int TABS = 1 << 1;

    /**
     * Shows a progress bar on top of the steps' content and partially fades the content out during operation.
     * @see ContentStepperFeedbackType
     */
    int CONTENT = 1 << 2;

    /**
     * Disables the buttons in the bottom navigation during operation.
     * @see DisabledBottomNavigationStepperFeedbackType
     */
    int DISABLED_BOTTOM_NAVIGATION = 1 << 3;

    int PROGRESS_ANIMATION_DURATION = 200;

    /**
     * Shows a progress indicator. This does not have to be a progress bar and it depends on chosen stepper feedback types.
     * @param progressMessage optional progress message if supported by the selected types
     */
    void showProgress(@NonNull String progressMessage);

    /**
     * Hides the progress indicator.
     */
    void hideProgress();

}
