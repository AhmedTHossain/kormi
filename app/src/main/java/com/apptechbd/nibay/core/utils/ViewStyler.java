package com.apptechbd.nibay.core.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

public class ViewStyler {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void applyStyledCard(
            View view,
            Context context,
            @ColorInt int backgroundColor,
            @ColorInt int strokeColor,
            float cornerRadiusDp,
            float strokeWidthDp,
            float elevationDp,
            @ColorInt int rippleColor
    ) {
        float density = context.getResources().getDisplayMetrics().density;
        float cornerRadiusPx = cornerRadiusDp * density;
        int strokeWidthPx = Math.round(strokeWidthDp * density);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(backgroundColor);
        drawable.setCornerRadius(cornerRadiusPx);
        if (strokeWidthPx > 0) {
            drawable.setStroke(strokeWidthPx, strokeColor);
        }

        RippleDrawable rippleDrawable = new RippleDrawable(
                ColorStateList.valueOf(rippleColor),
                drawable,
                null
        );

        view.setBackground(rippleDrawable);
        view.setElevation(elevationDp * density);
    }
}
