package com.stepstone.stepper.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

/**
 * Contains view information about the step.
 *
 * @author Piotr Zawadzki
 */
public class StepViewModel {

    private StepViewModel() {}

    /**
     * The displayable name of the step.
     */
    @Nullable
    private CharSequence title;

    /**
     * Allows to override the text on the Next button for this step.
     * To do so you need to return a non-null String of the label.
     * If you wish to change the text for selected steps only (and leave the default for the rest)
     * then return {@code null} for the default ones.
     * By default this is {@code null}.
     * This is not used for the last step.
     */
    @Nullable
    private CharSequence nextButtonLabel;

    /**
     * Allows to override the text on the Back button for this step.
     * To do so you need to return a non-null String of the label.
     * If you wish to change the text for selected steps only (and leave the default for the rest)
     * then return {@code null} for the default ones.
     * By default this is {@code null}.
     */
    @Nullable
    private CharSequence backButtonLabel;

    @Nullable
    public CharSequence getTitle() {
        return title;
    }

    @Nullable
    public CharSequence getNextButtonLabel() {
        return nextButtonLabel;
    }

    @Nullable
    public CharSequence getBackButtonLabel() {
        return backButtonLabel;
    }

    public static class Builder {

        @NonNull
        private final Context context;

        @Nullable
        private CharSequence title;

        @Nullable
        private CharSequence nextButtonLabel;

        @Nullable
        private CharSequence backButtonLabel;

        /**
         * Creates a builder for the step info.
         *
         * @param context the parent context
         */
        public Builder(@NonNull Context context) {
            this.context = context;
        }

        /**
         * Set the title using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@StringRes int titleId) {
            this.title = context.getString(titleId);
            return this;
        }

        /**
         * Set the title using the given characters.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@Nullable String title) {
            this.title = title;
            return this;
        }

        /**
         * Set the label of the next button using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNextButtonLabel(@StringRes int nextButtonLabelId) {
            this.nextButtonLabel = context.getString(nextButtonLabelId);
            return this;
        }

        /**
         * Set the label of the next button.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNextButtonLabel(@Nullable String nextButtonLabel) {
            this.nextButtonLabel = nextButtonLabel;
            return this;
        }

        /**
         * Set the label of the back button using the given resource id.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setBackButtonLabel(@StringRes int backButtonLabelId) {
            this.backButtonLabel = context.getString(backButtonLabelId);
            return this;
        }

        /**
         * Set the label of the back button.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setBackButtonLabel(@Nullable String backButtonLabel) {
            this.backButtonLabel = backButtonLabel;
            return this;
        }

        /**
         * Creates a {@link StepViewModel} with the arguments supplied to this
         * builder.
         */
        public StepViewModel create() {
            final StepViewModel viewModel = new StepViewModel();
            viewModel.title = this.title;
            viewModel.backButtonLabel = this.backButtonLabel;
            viewModel.nextButtonLabel = this.nextButtonLabel;
            return viewModel;
        }

    }
}
