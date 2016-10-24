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

package com.stepstone.stepper.util;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.widget.TextView;

/**
 * Utility class for tinting drawables/widgets.
 */
public class TintUtil {

    private static final String TAG = TintUtil.class.getSimpleName();

    /**
     * Tints TextView's text color and it's compound drawables
     * @param textview text view to tint
     * @param tintColor color state list to use for tinting
     */
    public static void tintTextView(@NonNull TextView textview, ColorStateList tintColor) {
        textview.setTextColor(tintColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Drawable[] drawables = textview.getCompoundDrawablesRelative();
            textview.setCompoundDrawablesRelative(
                    tintDrawable(drawables[0], tintColor),
                    tintDrawable(drawables[1], tintColor),
                    tintDrawable(drawables[2], tintColor),
                    tintDrawable(drawables[3], tintColor));
        } else {
            Drawable[] drawables = textview.getCompoundDrawables();
            textview.setCompoundDrawables(
                    tintDrawable(drawables[0], tintColor),
                    tintDrawable(drawables[1], tintColor),
                    tintDrawable(drawables[2], tintColor),
                    tintDrawable(drawables[3], tintColor));
        }
    }

    /**
     * Tints a drawable with the provided color
     * @param drawable drawable to tint
     * @param color tint color
     * @return tinted drawable
     */
    public static Drawable tintDrawable(@Nullable Drawable drawable, @ColorInt int color) {
        if (drawable != null) {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return drawable;
    }

    /**
     * Tints a drawable with the provided color state list
     * @param drawable drawable to tint
     * @param color tint color state list
     * @return tinted drawable
     */
    public static Drawable tintDrawable(@Nullable Drawable drawable, ColorStateList color) {
        if (drawable != null) {
            drawable = DrawableCompat.unwrap(drawable);
            Rect bounds = drawable.getBounds();
            drawable = DrawableCompat.wrap(drawable);
            // bounds can be all set to zeros when inflating vector drawables in Android Support Library 23.3.0...
            if (bounds.right == 0 || bounds.bottom == 0) {
                if (drawable.getIntrinsicHeight() != -1 && drawable.getIntrinsicWidth() != -1) {
                    bounds.right = drawable.getIntrinsicWidth();
                    bounds.bottom = drawable.getIntrinsicHeight();
                } else {
                    Log.w(TAG, "Cannot tint drawable because its bounds cannot be determined!");
                    return DrawableCompat.unwrap(drawable);
                }
            }
            DrawableCompat.setTintList(drawable, color);
            drawable.setBounds(bounds);
        }
        return drawable;
    }

}
