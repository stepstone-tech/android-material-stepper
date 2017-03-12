/*
Copyright 2016 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.stepstone.stepper.sample.step.fragment;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.widget.Toast;

import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.sample.R;

import butterknife.Bind;

public class DelayedTransitionStepFragmentSample extends ButterKnifeFragment implements BlockingStep {

    @Bind(R.id.operationSwitch)
    SwitchCompat operationSwitch;

    private AlertDialog dialog;

    public static DelayedTransitionStepFragmentSample newInstance() {
        return new DelayedTransitionStepFragmentSample();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }

    @Override
    @UiThread
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_loader);
        builder.setCancelable(false);
        dialog = builder.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                if (shouldOperationSucceed()) {
                    callback.goToNextStep();
                } else {
                    Activity activity = DelayedTransitionStepFragmentSample.this.getActivity();
                    if (activity != null && isResumed()) {
                        Toast.makeText(activity, "Operation failed!", Toast.LENGTH_SHORT).show();
                    }
                    callback.getStepperLayout().updateErrorState(true);
                }
            }
        }, 2000L);
    }

    /**
     * Notifies this step that the complete button/tab was clicked, the step was verified
     * and the user can complete the flow. This is so that the current step might perform
     * some last minute operations e.g. a network call before completing the flow.
     * {@link StepperLayout.OnCompleteClickedCallback} must be called once these operations finish.
     *
     * @param callback callback to call once the user wishes to complete the flow
     */
    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        // not needed here
    }

    private boolean shouldOperationSucceed() {
        return operationSwitch.isChecked();
    }

    @Override
    @UiThread
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        Toast.makeText(this.getContext(), "Your custom back action. Here you should cancel currently running operations", Toast.LENGTH_SHORT).show();
        callback.goToPrevStep();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_delayed_transition;
    }
}
