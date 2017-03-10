package com.stepstone.stepper.viewmodel;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.stepstone.stepper.R;

/**
 * Contains view information about the step.
 *
 * @author Piotr Zawadzki
 */
public class StepViewModel {

    /**
     * Drawable resource ID to be used for back/next navigation button compound drawables when we do not want to show them.
     * @see #mNextButtonEndDrawableResId
     * @see #mBackButtonStartDrawableResId
     */
    public static final int NULL_DRAWABLE = -1;

    private StepViewModel() {}

    /**
     * The displayable name of the step.
     */
    @Nullable
    private CharSequence mTitle;

    /**
     * Allows to override the text on the Next button for this step.
     * To do so you need to return a non-null String of the label.
     * If you wish to change the text for selected steps only (and leave the default for the rest)
     * then return {@code null} for the default ones.
     * By default this is {@code null}.
     * This is not used for the last step.
     */
    @Nullable
    private CharSequence mNextButtonLabel;

    /**
     * Allows to override the text on the Back button for this step.
     * To do so you need to return a non-null String of the label.
     * If you wish to change the text for selected steps only (and leave the default for the rest)
     * then return {@code null} for the default ones.
     * By default this is {@code null}.
     */
    @Nullable
    private CharSequence mBackButtonLabel;

    /**
     * Drawable resource ID to be used for next button's end compound drawable.
     * {@link com.stepstone.stepper.R.drawable#ms_ic_chevron_end} is the default.
     */
    @DrawableRes
    private int mNextButtonEndDrawableResId;

    /**
     * Drawable resource ID to be used for back button's start compound drawable.
     * {@link com.stepstone.stepper.R.drawable#ms_ic_chevron_start} is the default.
     */
    @DrawableRes
    private int mBackButtonStartDrawableResId;

    @Nullable
    public CharSequence getTitle() {
        return mTitle;
    }

    @Nullable
    public CharSequence getNextButtonLabel() {
        return mNextButtonLabel;
    }

    @Nullable
    public CharSequence getBackButtonLabel() {
        return mBackButtonLabel;
    }

    @DrawableRes
    public int getNextButtonEndDrawableResId() {
        return mNextButtonEndDrawableResId;
    }

    @DrawableRes
    public int getBackButtonStartDrawableResId() {
        return mBackButtonStartDrawableResId;
    }

    public static class Builder {

        @NonNull
        private final Context mContext;

        @Nullable
        private CharSequence mTitle;

        @Nullable
        private CharSequence mNextButtonLabel;

        @Nullable
        private CharSequence mBackButtonLabel;

        @DrawableRes
        private int mNextButtonEndDrawableResId = R.drawable.ms_ic_chevron_end;

        @DrawableRes
        private int mBackButtonStartDrawableResId = R.drawable.ms_ic_chevron_start;

        /**
         * Creates a builder for the step info.
         *
         * @param context the parent context
         */
        public Builder(@NonNull Context context) {
            this.mContext = context;
        }

        /**
         * Set the title using the given resource id.
         *
         * @param titleId string resource ID for the title
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@StringRes int titleId) {
            this.mTitle = mContext.getString(titleId);
            return this;
        }

        /**
         * Set the title using the given characters.
         *
         * @param title CharSequence to be used as a title
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setTitle(@Nullable CharSequence title) {
            this.mTitle = title;
            return this;
        }

        /**
         * Set the label of the next button using the given resource id.
         *
         * @param nextButtonLabelId string resource ID for the Next button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNextButtonLabel(@StringRes int nextButtonLabelId) {
            this.mNextButtonLabel = mContext.getString(nextButtonLabelId);
            return this;
        }

        /**
         * Set the label of the next button.
         *
         * @param nextButtonLabel CharSequence to be used as a Next button label
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNextButtonLabel(@Nullable CharSequence nextButtonLabel) {
            this.mNextButtonLabel = nextButtonLabel;
            return this;
        }

        /**
         * Set the label of the back button using the given resource id.
         *
         * @param backButtonLabelId string resource ID for the Back button
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setBackButtonLabel(@StringRes int backButtonLabelId) {
            this.mBackButtonLabel = mContext.getString(backButtonLabelId);
            return this;
        }

        /**
         * Set the label of the back button.
         *
         * @param backButtonLabel CharSequence to be used as a Back button label
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setBackButtonLabel(@Nullable CharSequence backButtonLabel) {
            this.mBackButtonLabel = backButtonLabel;
            return this;
        }

        /**
         * Set the drawable resource ID to be used for next button's end compound drawable.
         *
         * @param nextButtonEndDrawableResId drawable resource ID to be used for next button's end compound drawable.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setNextButtonEndDrawableResId(@DrawableRes int nextButtonEndDrawableResId) {
            this.mNextButtonEndDrawableResId = nextButtonEndDrawableResId;
            return this;
        }

        /**
         * Set the drawable resource ID to be used for back button's start compound drawable.
         *
         * @param backButtonStartDrawableResId drawable resource ID to be used for back button's start compound drawable.
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setBackButtonStartDrawableResId(@DrawableRes int backButtonStartDrawableResId) {
            this.mBackButtonStartDrawableResId = backButtonStartDrawableResId;
            return this;
        }

        /**
         * Creates a {@link StepViewModel} with the arguments supplied to this
         * builder.
         * @return created StepViewModel
         */
        public StepViewModel create() {
            final StepViewModel viewModel = new StepViewModel();
            viewModel.mTitle = this.mTitle;
            viewModel.mBackButtonLabel = this.mBackButtonLabel;
            viewModel.mNextButtonLabel = this.mNextButtonLabel;
            viewModel.mNextButtonEndDrawableResId = this.mNextButtonEndDrawableResId;
            viewModel.mBackButtonStartDrawableResId = this.mBackButtonStartDrawableResId;
            return viewModel;
        }

    }
}
