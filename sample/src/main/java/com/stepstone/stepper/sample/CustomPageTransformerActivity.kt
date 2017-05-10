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

package com.stepstone.stepper.sample

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View

class CustomPageTransformerActivity : AbstractStepperActivity() {

    override val layoutResId: Int
        get() = R.layout.activity_default_dots

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStepperLayout.setPageTransformer(CrossFadePageTransformer())
    }

    private class CrossFadePageTransformer : ViewPager.PageTransformer {

        override fun transformPage(view: View, position: Float) {
            if (position <= -1.0f || position >= 1.0f) {
                view.translationX = view.width * position
                view.alpha = 0.0f
            } else if (position == 0.0f) {
                view.translationX = view.width * position
                view.alpha = 1.0f
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.translationX = view.width * -position
                view.alpha = 1.0f - Math.abs(position)
            }
        }
    }
}
