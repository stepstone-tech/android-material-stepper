package com.stepstone.stepper.sample.nofrag;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.sample.OnNavigationBarListener;
import com.stepstone.stepper.sample.R;

/**
 * Created by leonardo on 18/12/16.
 */

public class NoFragView extends FrameLayout implements Step<View> {

    private static final int TAP_THRESHOLD = 2;

    private int i = 0;

    @Nullable
    private OnNavigationBarListener onNavigationBarListener;

    private Button button;

    public NoFragView(Context context) {
        super(context);
        init(context);
    }

    public NoFragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NoFragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public NoFragView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Context c = getContext();
        if(c instanceof OnNavigationBarListener){
            this.onNavigationBarListener = (OnNavigationBarListener) c;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.onNavigationBarListener = null;
    }

    private void init(Context context){
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_step, this, true);
        button = (Button) v.findViewById(R.id.button);

        updateNavigationBar();

        button = (Button) v.findViewById(R.id.button);
        button.setText(Html.fromHtml("Taps: <b>" + i + "</b>"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setText(Html.fromHtml("Taps: <b>" + (++i) + "</b>"));
                updateNavigationBar();
            }
        });
    }

    private boolean isAboveThreshold() {
        return i >= TAP_THRESHOLD;
    }

    @Override
    public int getName() {
        return R.string.app_name;
    }

    @Override
    public VerificationError verifyStep() {
        return isAboveThreshold() ? null : new VerificationError("Click " + (TAP_THRESHOLD - i) + " more times!");
    }

    private void updateNavigationBar() {
        if (onNavigationBarListener != null) {
            onNavigationBarListener.onChangeEndButtonsEnabled(isAboveThreshold());
        }
    }

    @Override
    public void onSelected() {
        updateNavigationBar();
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        button.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_error));
    }

    @Override
    public View getStepView() {
        return this;
    }
}
