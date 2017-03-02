/*
Copyright 2017 StepStone Services

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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.sample.OnProceedListener;
import com.stepstone.stepper.sample.R;

import butterknife.Bind;
import butterknife.OnEditorAction;

public class FormStepFragment extends ButterKnifeFragment implements Step {

    public static FormStepFragment newInstance() {
        return new FormStepFragment();
    }

    @Bind(R.id.editText)
    EditText editText;

    @Nullable
    private OnProceedListener onProceedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProceedListener) {
            onProceedListener = (OnProceedListener) context;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_step_form;
    }

    @Override
    public VerificationError verifyStep() {
        return TextUtils.isEmpty(editText.getText().toString())
                ? new VerificationError("Password cannot be empty")
                : null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        editText.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
    }

    @OnEditorAction(R.id.editText)
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE && onProceedListener != null) {
            onProceedListener.onProceed();
        }
        return true;
    }
}
