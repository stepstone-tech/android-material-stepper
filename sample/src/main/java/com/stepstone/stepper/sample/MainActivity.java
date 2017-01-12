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

package com.stepstone.stepper.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.defaultDots)
    public void onDefaultDots(View view) {
        startActivity(new Intent(this, DefaultDotsActivity.class));
    }

    @OnClick(R.id.styledDots)
    public void onStyledDots(View view) {
        startActivity(new Intent(this, StyledDotsActivity.class));
    }

    @OnClick(R.id.themedDots)
    public void onThemedDots(View view) {
        startActivity(new Intent(this, ThemedDotsActivity.class));
    }

    @OnClick(R.id.defaultProgressBar)
    public void onDefaultProgressBar(View view) {
        startActivity(new Intent(this, DefaultProgressBarActivity.class));
    }

    @OnClick(R.id.styledProgressBar)
    public void onStyledProgressBar(View view) {
        startActivity(new Intent(this, StyledProgressBarActivity.class));
    }

    @OnClick(R.id.defaultTabs)
    public void onDefaultTabs(View view) {
        startActivity(new Intent(this, DefaultTabsActivity.class));
    }

    @OnClick(R.id.styledTabs)
    public void onStyledTabs(View view) {
        startActivity(new Intent(this, StyledTabsActivity.class));
    }

    @OnClick(R.id.combination)
    public void onCombination(View view) {
        startActivity(new Intent(this, CombinationActivity.class));
    }

    @OnClick(R.id.customPageTransformer)
    public void onCustomPageTransformer(View view) {
        startActivity(new Intent(this, CustomPageTransformerActivity.class));
    }

    @OnClick(R.id.delayedTransition)
    public void onDelayedTransition(View view) {
        startActivity(new Intent(this, DelayedTransitionStepperActivity.class));
    }

    @OnClick(R.id.differentNavigationButtons)
    public void onDifferentNextButtons(View view) {
        startActivity(new Intent(this, CustomNavigationButtonsStepperActivity.class));
    }

    @OnClick(R.id.showReturnButtonOnFirstStep)
    public void onShowReturnButton(View view) {
        startActivity(new Intent(this, ReturnButtonActivity.class));
    }

    @OnClick(R.id.noFragments)
    public void onNoFrag(View view){
        startActivity(new Intent(this, NoFragmentsActivity.class));
    }

}
